package com.trip.safe.review.presentation.dto.response

import com.trip.safe.travel.domain.TravelDestination
import java.time.LocalDate

data class ReviewListResponse(
    val reviewList: List<ReviewElement>,
    val travelDestination: TravelDestination,
)

data class ReviewElement(
    val id: Long,
    val title: String,
    val content: String,
    val createdDate: LocalDate,
    val imageUrl1: String?,
    val imageUrl2: String?,
    val imageUrl3: String?,
    val isMine: Boolean,
    val accountId: String,
)

fun ReviewElement.toReviewElement(userAccountId: String) = ReviewElement(
    id = this.id,
    title = this.title,
    content = this.content,
    createdDate = this.createdDate,
    imageUrl1 = this.imageUrl1,
    imageUrl2 = this.imageUrl2,
    imageUrl3 = this.imageUrl3,
    isMine = this.accountId == userAccountId,
    accountId = this.accountId,
)
