package com.trip.safe.user.exception

import com.trip.safe.common.error.BaseException

class UserNotFoundException(
    errorMessage: String,
) : BaseException(errorMessage, 404) {
    companion object {
        const val USER_NOT_FOUND = "User Not Found"
    }
}
