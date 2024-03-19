package com.trip.safe.common.error.exception

data class ErrorResponse(
    val errorMessage: String,
    val statusCode: Int,
)
