package com.trip.safe.review.presentation.dto.request

data class UpdateReviewRequest(
    val title: String,
    val content: String,
    val imageUrl1: String?,
    val imageUrl2: String?,
    val imageUrl3: String?,
)
