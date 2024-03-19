package com.trip.safe.common.security.jwt

import com.trip.safe.user.presentation.dto.response.TokenResponse
import io.jsonwebtoken.Header.JWT_TYPE
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.Date

@Component
class JwtTokenProvider(
    private val jwtTokenProperties: JwtTokenProperties
) {

    private fun createToken(accountId: String): String = Jwts.builder()
        .signWith(SignatureAlgorithm.HS256, jwtTokenProperties.secretKey)
        .setHeaderParam(JWT_TYPE, JwtConstants.TOKEN_TYPE)
        .setSubject(accountId)
        .setIssuedAt(Date())
        .setExpiration(Date(System.currentTimeMillis() + jwtTokenProperties.accessTokenExp * 1000))
        .compact()

    fun getToken(accountId: String) = TokenResponse(
        accessToken = createToken(accountId),
        accessTokenExpiredAt = LocalDateTime.now().plusSeconds(jwtTokenProperties.accessTokenExp)
    )
}
