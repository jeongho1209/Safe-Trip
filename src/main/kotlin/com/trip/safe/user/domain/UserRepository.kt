package com.trip.safe.user.domain

import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface UserRepository : CoroutineCrudRepository<User, Long> {
    suspend fun findByAccountId(accountId: String): User?
}
