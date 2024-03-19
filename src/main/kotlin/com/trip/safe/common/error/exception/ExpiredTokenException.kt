package com.trip.safe.common.error.exception

import com.trip.safe.common.error.BaseException

class ExpiredTokenException(
    errorMessage: String,
) : BaseException(errorMessage, 401) {
    companion object {
        const val EXPIRED_TOKEN = "Expired Token"
    }
}
