package com.trip.safe.safeinfo.domain

import org.springframework.data.annotation.Id
import java.time.LocalDate

class CountrySafeInfo(
    @Id
    val id: Long,

    val title: String,
    val content: String,
    val createdDate: LocalDate,
    val travelDestinationId: Long,
)
