package com.trip.safe.common.error.exception

import com.trip.safe.common.error.BaseException

class UnAuthorizedException(
    errorMessage: String,
) : BaseException(errorMessage, 401) {
    companion object {
        const val UN_AUTHORIZED = "Un Authorized"
    }
}
