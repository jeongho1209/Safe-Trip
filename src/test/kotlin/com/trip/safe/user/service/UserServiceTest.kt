package com.trip.safe.user.service

import com.trip.safe.common.security.jwt.JwtTokenProvider
import com.trip.safe.user.domain.User
import com.trip.safe.user.domain.UserRepository
import com.trip.safe.user.exception.UserExistException
import com.trip.safe.user.presentation.dto.request.UserSignUpRequest
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.security.crypto.password.PasswordEncoder

class UserServiceTest : DescribeSpec(
    {
        val userRepository = mockk<UserRepository>(relaxed = true)
        val jwtTokenProvider = mockk<JwtTokenProvider>(relaxed = true)
        val passwordEncoder = mockk<PasswordEncoder>(relaxed = true)
        val userService = UserService(userRepository, jwtTokenProvider, passwordEncoder)
        val requestStub = UserSignUpRequest("testAccountId", "testPassword", 20)

        describe("유저가 회원가입을 하는 경우") {
            context("이미 존재하는 유저이면") {
                coEvery { userRepository.existsByAccountId(requestStub.accountId) } returns true
                it("UserExistException을 던진다.") {
                    assertThrows<UserExistException> { userService.signUp(requestStub) }
                    coVerify(exactly = 0) { userRepository.save(any()) }
                }
            }

            context("존재하지 않는 유저이면") {
                coEvery { userRepository.existsByAccountId(requestStub.accountId) } returns false
                coEvery { passwordEncoder.encode(requestStub.password) } returns requestStub.password
                it("회원가입에 성공한다.") {
                    assertDoesNotThrow { userService.signUp(requestStub) }
                    coVerify(exactly = 1) { userRepository.save(any()) }
                }
            }
        }
    }
)
