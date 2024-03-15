package com.trip.safe.user

import org.springframework.data.annotation.Id

class User(
    @Id
    val id: Int,

    val accountId: String,
    val password: String,
    val age: Short
)
