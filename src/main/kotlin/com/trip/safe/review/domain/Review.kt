package com.trip.safe.review.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table("review")
class Review(
    @Id
    var id: Long = 0,

    var title: String,
    var content: String,
    val createDate: LocalDate,
    var imageUrl1: String?,
    var imageUrl2: String?,
    var imageUrl3: String?,
    private var isDeleted: Boolean = false,
    val travelDestinationId: Long,
    val userId: Long,
) {
    fun updateReview(
        title: String,
        content: String,
        imageUrl1: String?,
        imageUrl2: String?,
        imageUrl3: String?
    ) {
        this.title = title
        this.content = content
        this.imageUrl1 = imageUrl1
        this.imageUrl2 = imageUrl2
        this.imageUrl3 = imageUrl3
    }

    fun deleteReview() {
        this.isDeleted = true
    }
}
