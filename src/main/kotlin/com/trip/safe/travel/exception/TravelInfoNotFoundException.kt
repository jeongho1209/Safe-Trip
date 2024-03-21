package com.trip.safe.travel.exception

import com.trip.safe.common.error.BaseException

class TravelInfoNotFoundException(
    errorMessage: String,
) : BaseException(errorMessage, 404) {
    companion object {
        const val TRAVEL_INFO_NOT_FOUND = "Travel Info Not Found"
    }
}
