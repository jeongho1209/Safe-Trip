package com.trip.safe.travel.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table("travel_info")
class TravelInfo(
    @Id
    var id: Long = 0,

    var title: String,
    var content: String,
    val createdDate: LocalDate,
    private var isDeleted: Boolean = false,
    var travelDestinationId: Long,
    val userId: Long,
) {
    fun updateTravelInfo(title: String, content: String) {
        this.title = title
        this.content = content
    }

    fun deleteTravelInfo() {
        this.isDeleted = true
    }
}
