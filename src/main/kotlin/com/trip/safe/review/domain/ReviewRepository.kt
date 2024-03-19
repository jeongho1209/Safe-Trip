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
            u.age
            from review as r
            inner join user as u
            on r.user_id = u.id
        """
    )
//    on r.travel_destination_id = :travelDestinationId
    suspend fun findAllByTravelDestinationId(travelDestinationId: Long): Flux<ReviewElement>
}
