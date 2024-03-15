package com.trip.safe.common.security.jwt

import io.jsonwebtoken.Header.JWT_TYPE
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JwtTokenProvider(
    private val jwtTokenProperties: JwtTokenProperties
) {

    fun createToken(accountId: String, tokenExpiration: Long): String = Jwts.builder()
        .signWith(SignatureAlgorithm.HS256, jwtTokenProperties.secretKey)
        .setHeaderParam(JWT_TYPE, "accessToken")
        .setSubject(accountId)
        .setIssuedAt(Date())
        .setExpiration(Date(System.currentTimeMillis() + tokenExpiration * 1000))
        .compact()
}
