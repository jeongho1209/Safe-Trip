package com.trip.safe.common.error.exception

data class BindErrorResponse(
    val responseStatus: Int,
    val fieldError: HashMap<String, String?>,
)
