package com.trip.safe.common.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun String.toLocalDate() = LocalDate.parse(this, DateTimeFormatter.ISO_DATE)
