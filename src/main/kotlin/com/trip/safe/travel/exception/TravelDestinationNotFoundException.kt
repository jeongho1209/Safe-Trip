package com.trip.safe.travel.exception

import com.trip.safe.common.error.BaseException

class TravelDestinationNotFoundException(
    errorMessage: String,
) : BaseException(errorMessage, 404) {
    companion object {
        const val TRAVEL_DESTINATION_NOT_FOUND = "TravelDestination Not Found"
    }
}
