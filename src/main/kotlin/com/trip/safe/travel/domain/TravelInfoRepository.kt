package com.trip.safe.travel.domain

import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface TravelInfoRepository : CoroutineCrudRepository<TravelInfo, Long>