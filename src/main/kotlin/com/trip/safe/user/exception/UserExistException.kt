package com.trip.safe.user.exception

import com.trip.safe.common.error.BaseException

class UserExistException(
    errorMessage: String,
) : BaseException(errorMessage, 409) {
    companion object {
        const val USER_EXIST = "User Exist"
    }
}
