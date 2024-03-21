package com.trip.safe.review.domain

import com.trip.safe.review.presentation.dto.response.ReviewElement
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import reactor.core.publisher.Flux

interface ReviewRepository : CoroutineCrudRepository<Review, Long> {

    @Query(
        """
            SELECT
            r.id,
            r.title, 
            r.content,
            r.createDate,
            r.imageUrl1,
            r.imageUrl2,
            r.imageUrl3,
            u.accountId
            FROM review AS r
            INNER JOIN user AS u
            ON r.user_id = u.id
            WHERE r.travel_destination_id = :travelDestinationId AND r.is_deleted = false
        """
    )
    suspend fun findAllByTravelDestinationId(travelDestinationId: Long): Flux<ReviewElement>
}
