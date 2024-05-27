package com.trip.safe.review.presentation

import com.trip.safe.review.domain.Review
import com.trip.safe.review.domain.ReviewRepository
import com.trip.safe.review.presentation.dto.request.CreateReviewRequest
import com.trip.safe.review.presentation.dto.request.UpdateReviewRequest
import com.trip.safe.review.presentation.dto.response.ReviewListResponse
import com.trip.safe.review.service.ReviewService
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RequestMapping("/review")
@RestController
class ReviewController(
    private val reviewService: ReviewService,
    private val reviewRepository: ReviewRepository,
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
        @RequestParam("type") type: String,
        pageable: Pageable,
    ): ReviewListResponse {
        return reviewService.getReviewsByTravelDestinationId(travelDestinationId, type, pageable)
    }

//    @GetMapping("/save")
//    suspend fun testSave() {
//        for (i in 1..1000) {
//            reviewRepository.save(
//                Review(
//                    title = "asf",
//                    content = "afs",
//                    createdDate = LocalDate.now(),
//                    imageUrl1 = null,
//                    imageUrl2 = null,
//                    imageUrl3 = null,
//                    travelDestinationId = 1,
//                    userId = 1
//                )
//            )
//        }
//    }
}
