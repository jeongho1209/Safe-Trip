package com.trip.safe.common.webclient.dto.response

data class CountrySafetyInfoList(
    val countrySafetyInfoList: List<CountrySafetyInfoElement>,
)

data class CountrySafetyInfoElement(
    val content: String,
    val countryEnName: String,
    val countryName: String,
    val id: String,
    val title: String,
    val wrtDt: String,
)
