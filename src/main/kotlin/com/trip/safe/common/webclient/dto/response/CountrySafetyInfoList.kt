package com.trip.safe.common.webclient.dto.response

import java.time.LocalDate

data class CountrySafetyInfoList(
    val countrySafetyInfoList: List<CountrySafetyInfoElement>,
)

data class CountrySafetyInfoElement(
    val content: String,
    val name: String,
    val engName: String,
    val code: String,
    val title: String,
    val createdDate: LocalDate,
)

fun CountrySafetyInfoElement.toCountrySafetyInfoElement() = CountrySafetyInfoElement(
    content = this.content,
    name = this.name,
    engName = this.engName,
    code = this.code,
    title = this.title,
    createdDate = this.createdDate,
)
