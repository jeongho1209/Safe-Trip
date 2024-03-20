package com.trip.safe.review.presentation

import com.trip.safe.review.presentation.dto.request.CreateReviewRequest
import com.trip.safe.review.presentation.dto.response.ReviewListResponse
import com.trip.safe.review.service.ReviewService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/reviews")
@RestController
class ReviewController(
    private val reviewService: ReviewService,
) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{travel-destination-id}")
    suspend fun createReview(
        @RequestBody @Valid request: CreateReviewRequest,
        @PathVariable("travel-destination-id") travelDestinationId: Long,
    ) {
        reviewService.createReview(request, travelDestinationId)
    }

    @GetMapping("/{travel-destination-id}")
    suspend fun getReviewsByTravelDestinationId(
        @PathVariable("travel-destination-id") travelDestinationId: Long,
    ): ReviewListResponse {
        return reviewService.getReviewsByTravelDestinationId(travelDestinationId)
    }
}
