package com.trip.safe.user.presentation.dto.request

data class UserSignUpRequest(
    val accountId: String,
    val password: String,
    val age: Short,
)
