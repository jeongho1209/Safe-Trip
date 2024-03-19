package com.trip.safe.review.presentation.dto.request

data class CreateReviewRequest(
    val title: String,
    val content: String,
    val imageUrl1: String?,
    val imageUrl2: String?,
    val imageUrl3: String?,
)
