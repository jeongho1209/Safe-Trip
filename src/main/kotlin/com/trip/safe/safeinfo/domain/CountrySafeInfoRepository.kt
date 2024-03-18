package com.trip.safe.safeinfo.domain

import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface CountrySafeInfoRepository : CoroutineCrudRepository<CountrySafeInfo, Long>
