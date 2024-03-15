package com.trip.safe.common.security.jwt

import com.trip.safe.common.security.auth.AuthDetailsService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.InvalidClaimException
import io.jsonwebtoken.Jwts
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class JwtTokenParser(
    private val jwtTokenProperties: JwtTokenProperties,
    private val authDetailsService: AuthDetailsService,
) {

    suspend fun getAuthentication(token: String): Authentication {
        val claims = getClaims(token)
        val authDetails = authDetailsService.findByUsername(claims.subject).awaitSingle()

        return UsernamePasswordAuthenticationToken(authDetails, "", authDetails.authorities)
    }

    private fun getClaims(token: String): Claims =
        runCatching {
            Jwts.parser()
                .setSigningKey(jwtTokenProperties.secretKey)
                .parseClaimsJws(token).body
        }.onFailure { exception ->
            when (exception) {
                is InvalidClaimException -> throw RuntimeException("")
                is ExpiredJwtException -> throw RuntimeException("")
                else -> throw RuntimeException("")
            }
        }.getOrThrow()
}
