package com.trip.safe.review.service

import com.trip.safe.common.error.exception.ForbiddenException
import com.trip.safe.common.security.SecurityFacade
import com.trip.safe.review.domain.Review
import com.trip.safe.review.domain.ReviewRepository
import com.trip.safe.review.exception.ReviewNotFoundException
import com.trip.safe.review.presentation.dto.request.CreateReviewRequest
import com.trip.safe.review.presentation.dto.request.UpdateReviewRequest
import com.trip.safe.review.presentation.dto.response.ReviewListResponse
import com.trip.safe.review.presentation.dto.response.toReviewElement
import com.trip.safe.travel.domain.TravelDestinationRepository
import com.trip.safe.travel.exception.TravelDestinationNotFoundException
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class ReviewService(
    private val travelDestinationRepository: TravelDestinationRepository,
    private val reviewRepository: ReviewRepository,
    private val securityFacade: SecurityFacade,
) {
    suspend fun createReview(request: CreateReviewRequest, travelDestinationId: Long) {
        val user = securityFacade.getCurrentUser()
        val travelDestination = travelDestinationRepository.findById(travelDestinationId)
            ?: throw TravelDestinationNotFoundException(TravelDestinationNotFoundException.TRAVEL_DESTINATION_NOT_FOUND)

        reviewRepository.save(
            Review(
                title = request.title,
                content = request.content,
                createdDate = LocalDate.now(),
                imageUrl1 = request.imageUrl1,
                imageUrl2 = request.imageUrl2,
                imageUrl3 = request.imageUrl3,
                travelDestinationId = travelDestination.id,
                userId = user.id
            )
        )
    }

    @Transactional
    suspend fun updateReview(request: UpdateReviewRequest, reviewId: Long) {
        val user = securityFacade.getCurrentUser()

        val review = reviewRepository.findById(reviewId)
            ?: throw ReviewNotFoundException(ReviewNotFoundException.REVIEW_NOT_FOUND)

        if (review.userId != user.id) {
            throw ForbiddenException(ForbiddenException.FORBIDDEN)
        }

        review.updateReview(
            title = request.title,
            content = request.content,
            imageUrl1 = request.imageUrl1,
            imageUrl2 = request.imageUrl2,
            imageUrl3 = request.imageUrl3
        )
    }

    @Transactional
    suspend fun deleteReview(reviewId: Long) {
        val user = securityFacade.getCurrentUser()

        val review = reviewRepository.findById(reviewId)
            ?: throw ReviewNotFoundException(ReviewNotFoundException.REVIEW_NOT_FOUND)

        if (review.userId != user.id) {
            throw ForbiddenException(ForbiddenException.FORBIDDEN)
        }

        review.deleteReview()
    }

    suspend fun getReviewsByTravelDestinationId(
        travelDestinationId: Long,
        type: String,
        pageable: Pageable,
    ): ReviewListResponse {
        val user = securityFacade.getCurrentUser()
        val reviewList = reviewRepository.findAllByTravelDestinationId(
            travelDestinationId = travelDestinationId,
            limit = pageable.pageSize,
            offset = pageable.offset,
        ).collectList().awaitSingle()

        val travelDestination = travelDestinationRepository.findById(travelDestinationId)
            ?: throw TravelDestinationNotFoundException(TravelDestinationNotFoundException.TRAVEL_DESTINATION_NOT_FOUND)

        return ReviewListResponse(
            reviewList = reviewList.map { it.toReviewElement(user.accountId) },
            travelDestination = travelDestination,
        )
    }
}
