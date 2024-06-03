package com.trip.safe.travel.presentation.dto.response

import com.trip.safe.travel.domain.TravelDestination
import java.time.LocalDate

data class TravelInfoListResponse(
    val travelInfoList: List<TravelInfoElement>,
    val travelDestination: TravelDestination,
)

data class TravelInfoElement(
    val id: Long,
    val title: String,
    val content: String,
    val createdDate: LocalDate,
    val accountId: String,
    val isMine: Boolean?,
)

data class MyTravelInfoElement(
    val id: Long,
    val title: String,
    val content: String,
    val name: String
)

fun TravelInfoElement.toTravelInfoElement(isMine: Boolean) = TravelInfoElement(
    id = this.id,
    title = this.title,
    content = this.content,
    createdDate = this.createdDate,
    accountId = this.accountId,
    isMine = isMine,
)
