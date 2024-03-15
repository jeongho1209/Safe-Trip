package com.trip.safe.common.security.auth

import com.trip.safe.user.UserRepository
import kotlinx.coroutines.reactor.mono
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class AuthDetailsService(
    private val userRepository: UserRepository,
) : ReactiveUserDetailsService {

    override fun findByUsername(accountId: String): Mono<UserDetails> = mono {
        val user = userRepository.findByAccountId(accountId)
            ?: throw RuntimeException("") // TODO: error handling

        return@mono AuthDetails(user)
    }
}
