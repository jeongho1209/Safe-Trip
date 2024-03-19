package com.trip.safe.safeinfo.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table("country_safe_info")
class CountrySafeInfo(
    @Id
    val id: Long,

    val title: String,
    val content: String,
    val createdDate: LocalDate,
    val travelDestinationId: Long,
)
