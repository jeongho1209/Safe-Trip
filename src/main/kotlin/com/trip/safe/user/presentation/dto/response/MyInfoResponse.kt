package com.trip.safe.user.presentation.dto.response

import com.trip.safe.review.presentation.dto.response.ReviewElement
import com.trip.safe.travel.presentation.dto.response.MyTravelInfoElement

data class MyInfoResponse(
    val accountId: String,
    val age: Short,
    val reviewList: List<ReviewElement>,
    val travelInfoList: List<MyTravelInfoElement>,
)
