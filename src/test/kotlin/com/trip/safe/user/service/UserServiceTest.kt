package com.trip.safe.user.service

import com.trip.safe.common.security.jwt.JwtTokenProvider
import com.trip.safe.user.domain.User
import com.trip.safe.user.domain.UserRepository
import com.trip.safe.user.exception.PasswordMisMatchException
import com.trip.safe.user.exception.UserExistException
import com.trip.safe.user.exception.UserNotFoundException
import com.trip.safe.user.presentation.dto.request.UserSignInRequest
import com.trip.safe.user.presentation.dto.request.UserSignUpRequest
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.security.crypto.password.PasswordEncoder

class UserServiceTest : DescribeSpec(
    {
        val userRepository = mockk<UserRepository>(relaxed = true)
        val jwtTokenProvider = mockk<JwtTokenProvider>(relaxed = true)
        val passwordEncoder = mockk<PasswordEncoder>(relaxed = true)
        val userService = UserService(userRepository, jwtTokenProvider, passwordEncoder)
        val signUpRequestStub = UserSignUpRequest("testAccountId", "testPassword", 20)
        val signInRequestStub = UserSignInRequest("testAccountId", "testPassword")
        val userStub = User(
            accountId = "userAccountId",
            password = "userPassword",
            age = 20,
        )

        describe("유저가 회원가입을 하는 경우") {
            context("이미 존재하는 유저이면") {
                coEvery { userRepository.existsByAccountId(signUpRequestStub.accountId) } returns true
                it("UserExistException을 던진다.") {
                    assertThrows<UserExistException> { userService.signUp(signUpRequestStub) }
                    coVerify(exactly = 0) { userRepository.save(any()) }
                }
            }

            context("존재하지 않는 유저이면") {
                coEvery { userRepository.existsByAccountId(signUpRequestStub.accountId) } returns false
                coEvery { passwordEncoder.encode(signUpRequestStub.password) } returns signUpRequestStub.password
                it("회원가입에 성공한다.") {
                    assertDoesNotThrow { userService.signUp(signUpRequestStub) }
                    coVerify(exactly = 1) { userRepository.save(any()) }
                }
            }
        }

        describe("유저가 로그인을 하는 경우") {
            context("잘못된 아이디라면") {
                coEvery { userRepository.findByAccountId(signInRequestStub.accountId) } returns null
                it("UserNotFoundException을 던진다.") {
                    assertThrows<UserNotFoundException> { userService.signIn(signInRequestStub) }
                }
            }

            context("잘못된 비밀번호라면") {
                coEvery { userRepository.findByAccountId(signInRequestStub.accountId) } returns userStub
                coEvery { passwordEncoder.matches(any(), any()) } returns false
                it("PasswordMisMatchException을 던진다.") {
                    assertThrows<PasswordMisMatchException> { userService.signIn(signInRequestStub) }
                }
            }

            context("올바른 아이디, 비밀번호라면") {
                coEvery { userRepository.findByAccountId(signInRequestStub.accountId) } returns userStub
                coEvery { passwordEncoder.matches(any(), any()) } returns true
                it("로그인에 성공한다.") {
                    assertDoesNotThrow { userService.signIn(signInRequestStub) }
                }
            }
        }
    }
)
