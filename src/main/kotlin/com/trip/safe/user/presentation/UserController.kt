package com.trip.safe.user.presentation

import com.trip.safe.user.presentation.dto.request.UserSignInRequest
import com.trip.safe.user.presentation.dto.request.UserSignUpRequest
import com.trip.safe.user.presentation.dto.response.MyInfoResponse
import com.trip.safe.user.presentation.dto.response.TokenResponse
import com.trip.safe.user.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/users")
@RestController
class UserController(
    private val userService: UserService,
) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    suspend fun signUp(
        @RequestBody @Valid request: UserSignUpRequest
    ): TokenResponse {
        return userService.signUp(request)
    }

    @PostMapping("/login")
    suspend fun signIn(
        @RequestBody @Valid request: UserSignInRequest
    ): TokenResponse {
        return userService.signIn(request)
    }

    @GetMapping("/my")
    suspend fun getMyInfo(): MyInfoResponse {
        return userService.getMyInfo()
    }
}
