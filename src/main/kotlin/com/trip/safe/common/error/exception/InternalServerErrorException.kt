package com.trip.safe.common.error.exception

class InternalServerErrorException(
    errorMessage: String,
) : BaseException(errorMessage, 500) {
    companion object {
        const val UNEXPECTED_ERROR = "Un Expected Error"
    }
}
