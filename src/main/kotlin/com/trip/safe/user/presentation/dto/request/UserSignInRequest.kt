package com.trip.safe.user.presentation.dto.request

data class UserSignInRequest(
    val accountId: String,
    val password: String,
)
