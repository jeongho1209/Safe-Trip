package com.trip.safe.travel.presentation.dto.response

import com.trip.safe.travel.domain.TravelDestination

data class TravelDestinationListResponse(
    val travelDestinationList: List<TravelDestinationElement>,
)

data class TravelDestinationElement(
    val id: Long,
    val name: String,
    val engName: String,
    val code: String,
)

fun TravelDestination.toTravelDestinationElement() = TravelDestinationElement(
    id = this.id,
    name = this.name,
    engName = this.engName,
    code = this.code,
)
