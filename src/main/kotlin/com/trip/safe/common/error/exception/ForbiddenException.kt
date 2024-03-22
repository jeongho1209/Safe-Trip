package com.trip.safe.common.error.exception

import com.trip.safe.common.error.BaseException

class ForbiddenException(
    errorMessage: String,
) : BaseException(errorMessage, 403) {
    companion object {
        const val FORBIDDEN = "Forbidden"
    }
}
