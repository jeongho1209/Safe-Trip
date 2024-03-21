package com.trip.safe.common.error.exception

import com.trip.safe.common.error.BaseException

class BadRequestException(
    errorMessage: String,
) : BaseException(errorMessage, 400) {
    companion object {
        const val BAD_REQUEST = "Bad Request"
    }
}
