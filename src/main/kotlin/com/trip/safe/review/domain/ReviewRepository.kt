package com.trip.safe.review.domain

import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface ReviewRepository : CoroutineCrudRepository<Review, Long>
