package com.trip.safe.safeinfo.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table("country_safe_info")
class CountrySafeInfo(
    @Id
    var id: Long = 0,

    val title: String,
    val content: String,
    val createdDate: LocalDate,
    val travelDestinationId: Long,
)
