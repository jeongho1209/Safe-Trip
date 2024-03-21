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
    val createDate: LocalDate,
    val accountId: String,
    val isMine: Boolean,
)

fun TravelInfoElement.toTravelInfoElement() = TravelInfoElement(
    id = this.id,
    title = this.title,
    content = this.content,
    createDate = this.createDate,
    accountId = this.accountId,
    isMine = this.isMine,
)
