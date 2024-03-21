package com.trip.safe.review.exception

import com.trip.safe.common.error.BaseException

class ReviewNotFoundException(
    errorMessage: String,
) : BaseException(errorMessage, 404) {
    companion object {
        const val REVIEW_NOT_FOUND = "Review Not Found"
    }
}
