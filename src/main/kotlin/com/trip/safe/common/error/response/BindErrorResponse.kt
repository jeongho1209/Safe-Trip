package com.trip.safe.common.error.response

data class BindErrorResponse(
    val responseStatus: Int,
    val fieldError: HashMap<String, String?>,
)
