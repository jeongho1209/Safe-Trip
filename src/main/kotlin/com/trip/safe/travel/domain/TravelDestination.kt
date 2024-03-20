package com.trip.safe.travel.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("travel_destination")
class TravelDestination(
    @Id
    var id: Long,

    val name: String,
    val engName: String,
    val code: String,
)
