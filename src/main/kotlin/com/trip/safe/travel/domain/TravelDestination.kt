package com.trip.safe.travel.domain

import org.springframework.data.annotation.Id

class TravelDestination(
    @Id
    val id: Long,

    val name: String,
    val engName: String,
    val code: String,
)
