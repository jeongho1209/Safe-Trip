package com.trip.safe.review.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table("review")
class Review(
    @Id
    val id: Long = 0,

    val title: String,
    val content: String,
    val createDate: LocalDate,
    val imageUrl1: String?,
    val imageUrl2: String?,
    val imageUrl3: String?,
    val travelDestinationId: Long,
    val userId: Long,
)
