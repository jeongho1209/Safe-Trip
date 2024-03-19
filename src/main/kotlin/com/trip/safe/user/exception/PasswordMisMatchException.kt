package com.trip.safe.user.exception

import com.trip.safe.common.error.BaseException

class PasswordMisMatchException(
    errorMessage: String,
) : BaseException(errorMessage, 401) {
    companion object {
        const val PASSWORD_MIS_MATCH = "Password Mis Match"
    }
}
