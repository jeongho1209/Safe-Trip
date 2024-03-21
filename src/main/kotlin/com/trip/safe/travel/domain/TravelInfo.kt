package com.trip.safe.travel.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table("travel_info")
class TravelInfo(
    @Id
    var id: Long = 0,

    val title: String,
    val content: String,
    val createDate: LocalDate,
    val travelDestinationId: Long,
    val userId: Long,
)
