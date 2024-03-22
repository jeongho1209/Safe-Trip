package com.trip.safe.travel.service

import com.trip.safe.travel.domain.TravelDestinationRepository
import com.trip.safe.travel.presentation.dto.response.TravelDestinationListResponse
import com.trip.safe.travel.presentation.dto.response.toTravelDestinationElement
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class TravelDestinationService(
    private val travelDestinationRepository: TravelDestinationRepository,
) {

    suspend fun getAllTravelDestinationList(pageable: Pageable): TravelDestinationListResponse {
        val travelDestinationList = travelDestinationRepository.findAllBy(pageable)
            .collectList()
            .awaitSingle()

        return TravelDestinationListResponse(
            travelDestinationList = travelDestinationList.map { it.toTravelDestinationElement() }
        )
    }

    suspend fun getTravelDestinationListByName(name: String, pageable: Pageable): TravelDestinationListResponse {
        val travelDestinationList = travelDestinationRepository.findAllByName(
            name = name,
            pageable = pageable
        ).collectList().awaitSingle()

        return TravelDestinationListResponse(
            travelDestinationList = travelDestinationList.map { it.toTravelDestinationElement() }
        )
    }
}
