package com.trip.safe.travel.domain

import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface TravelDestinationRepository : CoroutineCrudRepository<TravelDestination, Long>
