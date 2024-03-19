package com.trip.safe.common.error

import com.trip.safe.common.error.response.BindErrorResponse
import com.trip.safe.common.error.response.ErrorResponse
import com.trip.safe.common.error.exception.InternalServerErrorException
import com.trip.safe.common.error.exception.RequestHandlerNotFoundException
import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.context.ApplicationContext
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.stereotype.Component
import org.springframework.validation.BindingResult
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono

@Order(-2)
@Component
class GlobalErrorHandler(
    errorAttributes: ErrorAttributes,
    webProperties: WebProperties,
    applicationContext: ApplicationContext,
    serverCodecConfigurer: ServerCodecConfigurer,
) : AbstractErrorWebExceptionHandler(
    errorAttributes,
    webProperties.resources,
    applicationContext,
) {

    init {
        super.setMessageReaders(serverCodecConfigurer.readers)
        super.setMessageWriters(serverCodecConfigurer.writers)
    }

    override fun getRoutingFunction(errorAttributes: ErrorAttributes): RouterFunction<ServerResponse> =
        RouterFunctions.route(RequestPredicates.all(), this::handleError)

    private fun handleError(request: ServerRequest): Mono<ServerResponse> =
        when (val e = super.getError(request)) {
            is BaseException -> e.toErrorResponse()
            is WebExchangeBindException -> e.getBindErrorMessage()
            is ResponseStatusException -> RequestHandlerNotFoundException(RequestHandlerNotFoundException.REQUEST_HANDLER_NOT_FOUND).toErrorResponse()
            else -> {
                e.printStackTrace()
                InternalServerErrorException(InternalServerErrorException.UNEXPECTED_ERROR).toErrorResponse()
            }
        }

    private fun BindingResult.getBindErrorMessage(): Mono<ServerResponse> {
        val errorMap = HashMap<String, String?>()

        this.fieldErrors.forEach { error ->
            errorMap[error.field] = error.defaultMessage
        }

        return ServerResponse.status(HttpStatus.BAD_REQUEST)
            .bodyValue(
                BindErrorResponse(
                    responseStatus = HttpStatus.BAD_REQUEST.value(),
                    fieldError = errorMap,
                )
            )
    }

    private fun ExceptionAttribute.toErrorResponse() =
        ServerResponse.status(this.responseStatus)
            .bodyValue(
                ErrorResponse(
                    errorMessage = this.errorMessage,
                    statusCode = this.responseStatus,
                ),
            )
}
