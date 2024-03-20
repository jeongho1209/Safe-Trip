package com.trip.safe.travel.domain

import org.springframework.data.domain.Pageable
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import reactor.core.publisher.Flux

interface TravelDestinationRepository : CoroutineCrudRepository<TravelDestination, Long> {

    suspend fun findAllBy(pageable: Pageable): Flux<TravelDestination>
}
