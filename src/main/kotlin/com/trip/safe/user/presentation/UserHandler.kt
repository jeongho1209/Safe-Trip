package com.trip.safe.user.presentation

import com.trip.safe.user.presentation.dto.request.UserSignInRequest
import com.trip.safe.user.presentation.dto.request.UserSignUpRequest
import com.trip.safe.user.service.UserService
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyToMono
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import java.net.URI

@Component
class UserHandler(
    private val userService: UserService,
) {

    suspend fun signUp(serverRequest: ServerRequest): ServerResponse {
        val requestBody = serverRequest.getUserSignUpRequestBody()
        val tokenResponse = userService.signUp(requestBody)
        return ServerResponse.created(URI("/users")).bodyValueAndAwait(tokenResponse)
    }

    private suspend fun ServerRequest.getUserSignUpRequestBody() =
        this.bodyToMono<UserSignUpRequest>().awaitSingle()

    suspend fun signIn(serverRequest: ServerRequest): ServerResponse {
        val requestBody = serverRequest.getUserSignInRequestBody()
        val tokenResponse = userService.signIn(requestBody)
        return ServerResponse.ok().bodyValueAndAwait(tokenResponse)
    }

    private suspend fun ServerRequest.getUserSignInRequestBody() =
        this.bodyToMono<UserSignInRequest>().awaitSingle()
}
