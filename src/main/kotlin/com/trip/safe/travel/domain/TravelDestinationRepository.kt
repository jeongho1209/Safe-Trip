package com.trip.safe.travel.domain

import com.trip.safe.common.webclient.dto.response.CountrySafetyInfoElement
import org.springframework.data.domain.Pageable
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import reactor.core.publisher.Flux

interface TravelDestinationRepository : CoroutineCrudRepository<TravelDestination, Long> {

    suspend fun findAllBy(pageable: Pageable): Flux<TravelDestination>
    suspend fun findAllByName(name: String, pageable: Pageable): Flux<TravelDestination>

    @Query(
        """
            SELECT 
                csi.content,
                td.name,
                td.eng_name,
                td.code,
                csi.title,
                csi.created_date
            FROM travel_destination AS td
            INNER JOIN country_safe_info AS csi
            ON csi.travel_destination_id = td.id
            WHERE td.code = :code
        """
    )
    suspend fun findByCode(code: String): CountrySafetyInfoElement?

    @Query(
        """
            SELECT 
                csi.content,
                td.name,
                td.eng_name,
                td.code,
                csi.title,
                csi.created_date
            FROM travel_destination AS td
            INNER JOIN country_safe_info AS csi
            ON csi.travel_destination_id = td.id
            WHERE td.name = :name or td.eng_name = :engName
            ORDER BY csi.created_date DESC
            LIMIT :limit
            OFFSET :offset
        """
    )
    suspend fun findAllByNameOrEngName(
        name: String?,
        engName: String?,
        limit: Int,
        offset: Long,
    ): Flux<CountrySafetyInfoElement>
}
