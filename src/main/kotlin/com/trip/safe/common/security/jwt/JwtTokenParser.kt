package com.trip.safe.common.security.jwt

import com.trip.safe.common.error.exception.ExpiredTokenException
import com.trip.safe.common.error.exception.InternalServerErrorException
import com.trip.safe.common.error.exception.InvalidSignatureException
import com.trip.safe.common.error.exception.InvalidTokenException
import com.trip.safe.common.security.auth.AuthDetailsService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.InvalidClaimException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureException
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
                is InvalidClaimException -> throw InvalidTokenException(InvalidTokenException.INVALID_TOKEN)
                is ExpiredJwtException -> throw ExpiredTokenException(ExpiredTokenException.EXPIRED_TOKEN)
                is SignatureException -> throw InvalidSignatureException(InvalidSignatureException.INVALID_SIGNATURE)
                else -> {
                    exception.printStackTrace()
                    throw InternalServerErrorException(InternalServerErrorException.UNEXPECTED_ERROR)
                }
            }
        }.getOrThrow()
}
