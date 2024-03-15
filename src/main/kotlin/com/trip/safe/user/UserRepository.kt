package com.trip.safe.user

import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface UserRepository : CoroutineCrudRepository<User, Int> {
    suspend fun findByAccountId(accountId: String): User?
}
