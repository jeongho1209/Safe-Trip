package com.trip.safe.travel.presentation.dto.response

import java.time.LocalDate

data class MyTravelInfoListResponse(
    val myTravelInfoList: List<MyTravelInfoElement>,
)

data class MyTravelInfoElement(
    val id: Long,
    val title: String,
    val content: String,
    val createdDate: LocalDate,
)

fun MyTravelInfoElement.toMyTravelInfoElement() = MyTravelInfoElement(
    id = this.id,
    title = this.title,
    content = this.content,
    createdDate = this.createdDate,
)
