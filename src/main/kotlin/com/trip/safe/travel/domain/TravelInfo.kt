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
    val createDate: LocalDate,
    private var isDeleted: Boolean = false,
    var travelDestinationId: Long,
    val userId: Long,
) {
    fun updateTravelInfo(title: String, content: String, travelDestinationId: Long) {
        this.title = title
        this.content = content
        this.travelDestinationId = travelDestinationId
    }

    fun deleteTravelInfo() {
        this.isDeleted = true
    }
}
