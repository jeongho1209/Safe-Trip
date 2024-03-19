package com.trip.safe.review.service

import com.trip.safe.common.security.SecurityFacade
import com.trip.safe.review.domain.Review
import com.trip.safe.review.domain.ReviewRepository
import com.trip.safe.review.presentation.dto.request.CreateReviewRequest
import com.trip.safe.review.presentation.dto.response.ReviewListResponse
import com.trip.safe.review.presentation.dto.response.toReviewElement
import com.trip.safe.travel.domain.TravelDestinationRepository
import com.trip.safe.travel.exception.TravelDestinationNotFoundException
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
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
                createDate = LocalDate.now(),
                imageUrl1 = request.imageUrl1,
                imageUrl2 = request.imageUrl2,
                imageUrl3 = request.imageUrl3,
                travelDestinationId = travelDestination.id,
                userId = user.id
            )
        )
    }

    suspend fun getReviewsByTravelDestinationId(travelDestinationId: Long): ReviewListResponse {
        val reviewList = reviewRepository.findAllByTravelDestinationId(travelDestinationId).collectList().awaitSingle()
        val travelDestination = travelDestinationRepository.findById(travelDestinationId)
            ?: throw TravelDestinationNotFoundException(TravelDestinationNotFoundException.TRAVEL_DESTINATION_NOT_FOUND)

        return ReviewListResponse(
            reviewList = reviewList.map { it.toReviewElement() },
            travelDestination = travelDestination,
        )
    }
}
