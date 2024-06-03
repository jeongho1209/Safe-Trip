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
            r.created_date,
            r.image_url1,
            r.image_url2,
            r.image_url3,
            u.account_id
            FROM review AS r
            INNER JOIN user AS u
            ON r.user_id = u.id
            WHERE r.travel_destination_id = :travelDestinationId AND r.is_deleted = false
            ORDER BY created_date DESC
            LIMIT :limit
            OFFSET :offset
        """
    )
    suspend fun findAllByTravelDestinationId(travelDestinationId: Long, limit: Int, offset: Long): Flux<ReviewElement>

    @Query(
        """
            SELECT
            r.id,
            r.title, 
            r.content,
            r.created_date,
            r.image_url1,
            r.image_url2,
            r.image_url3,
            u.account_id
            FROM review AS r
            INNER JOIN user AS u
            ON r.user_id = u.id
            WHERE r.user_id = :userId AND r.is_deleted = false 
            ORDER BY created_date DESC
        """
    )
    suspend fun findAllByUserId(userId: Long): Flux<ReviewElement>
}
