package com.trip.safe.common.error.response

data class ErrorResponse(
    val errorMessage: String,
    val statusCode: Int,
)
