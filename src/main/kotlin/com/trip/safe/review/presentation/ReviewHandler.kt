package com.trip.safe.review.presentation

import com.trip.safe.review.presentation.dto.request.CreateReviewRequest
import com.trip.safe.review.service.ReviewService
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyToMono
import org.springframework.web.reactive.function.server.buildAndAwait
import java.net.URI

@Component
class ReviewHandler(
    private val reviewService: ReviewService,
) {

    suspend fun createReview(serverRequest: ServerRequest): ServerResponse {
        val requestBody = serverRequest.getCreateReviewRequestBody()
        reviewService.createReview(requestBody, serverRequest.pathVariable("travel-destination-id").toLong())

        return ServerResponse.created(URI("")).buildAndAwait()
    }

    private suspend fun ServerRequest.getCreateReviewRequestBody() =
        this.bodyToMono<CreateReviewRequest>().awaitSingle()
}
