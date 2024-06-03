package com.trip.safe.travel.domain

import com.trip.safe.travel.presentation.dto.response.MyTravelInfoElement
import com.trip.safe.travel.presentation.dto.response.TravelInfoElement
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import reactor.core.publisher.Flux

interface TravelInfoRepository : CoroutineCrudRepository<TravelInfo, Long> {

    @Query(
        """
            SELECT
                ti.id,
                ti.title,
                ti.content,
                ti.created_date,
                u.account_id
            FROM travel_info AS ti
            INNER JOIN user AS u
            ON ti.user_id = u.id
            WHERE ti.travel_destination_id = :travelDestinationId AND ti.is_deleted = false
            ORDER BY ti.created_date DESC
            LIMIT :limit
            OFFSET :offset
        """
    )
    suspend fun findAllByTravelDestinationId(
        travelDestinationId: Long,
        limit: Int,
        offset: Long,
    ): Flux<TravelInfoElement>

    @Query(
        """
            SELECT
                ti.id,
                ti.title,
                ti.content,
                td.name
            FROM travel_info AS ti
            INNER JOIN user AS u
            ON ti.user_id = u.id
            INNER JOIN travel_destination AS td
            ON ti.travel_destination_id = td.id
            WHERE ti.user_id = :userId AND ti.is_deleted = false
            ORDER BY ti.created_date DESC
        """
    )
    suspend fun findAllByUserId(userId: Long): Flux<MyTravelInfoElement>
}
