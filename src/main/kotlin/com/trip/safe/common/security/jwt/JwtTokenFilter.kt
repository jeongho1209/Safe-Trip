package com.trip.safe.common.security.jwt

import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.reactor.mono
import org.springframework.core.annotation.Order
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Order(-100)
@Component
class JwtTokenFilter(
    private val jwtTokenParser: JwtTokenParser,
) : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> = mono {
        validateToken(exchange.request)?.run {
            val auth = jwtTokenParser.getAuthentication(this)
            return@mono chain.filter(exchange)
                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth))
                .awaitSingleOrNull()
        }
        return@mono chain.filter(exchange).awaitSingleOrNull()
    }


    private fun validateToken(request: ServerHttpRequest): String? =
        request.headers[JwtConstants.HEADER]?.get(0)?.let { token ->
            val isInvalidPrefix = !token.startsWith(JwtConstants.PREFIX)

            if (isInvalidPrefix) {
                return null
            }

            return token.substring(7)
        }
}
