package com.trip.safe.review.presentation

import com.trip.safe.review.presentation.dto.request.CreateReviewRequest
import com.trip.safe.review.presentation.dto.request.UpdateReviewRequest
import com.trip.safe.review.presentation.dto.response.ReviewListResponse
import com.trip.safe.review.service.ReviewService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
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

    @PatchMapping("/{review-id}")
    suspend fun updateReview(
        @RequestBody @Valid request: UpdateReviewRequest,
        @PathVariable("review-id") reviewId: Long,
    ) {
        reviewService.updateReview(request, reviewId)
    }

    @DeleteMapping("/{review-id}")
    suspend fun deleteReview(
        @PathVariable("review-id") reviewId: Long,
    ) {
        reviewService.deleteReview(reviewId)
    }

    @GetMapping("/{travel-destination-id}")
    suspend fun getReviewsByTravelDestinationId(
        @PathVariable("travel-destination-id") travelDestinationId: Long,
    ): ReviewListResponse {
        return reviewService.getReviewsByTravelDestinationId(travelDestinationId)
    }
}
