package com.trip.safe.common.security.jwt

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class JwtTokenProperties(
    val secretKey: String,
    val accessTokenExp: Long,
)
