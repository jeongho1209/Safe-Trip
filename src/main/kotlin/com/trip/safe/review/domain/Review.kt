package com.trip.safe.review.domain

import org.springframework.data.annotation.Id
import java.time.LocalDate

class Review(
    @Id
    val id: Long,

    val title: String,
    val content: String,
    val createDate: LocalDate,
    val imageUrl1: String?,
    val imageUrl2: String?,
    val imageUrl3: String?,
    val travelDestinationId: Long,
    val userId: Long,
)
