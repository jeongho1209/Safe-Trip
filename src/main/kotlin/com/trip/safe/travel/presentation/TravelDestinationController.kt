package com.trip.safe.travel.presentation

import com.trip.safe.travel.domain.TravelDestination
import com.trip.safe.travel.domain.TravelDestinationRepository
import com.trip.safe.travel.presentation.dto.response.TravelDestinationListResponse
import com.trip.safe.travel.service.TravelDestinationService
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/travel")
@RestController
class TravelDestinationController(
    private val travelDestinationService: TravelDestinationService,
    private val travelDestinationRepository: TravelDestinationRepository,
) {

    @GetMapping("/all")
    suspend fun getAllTravelDestinationList(pageable: Pageable): TravelDestinationListResponse {
        return travelDestinationService.getAllTravelDestinationList(pageable)
    }

    // save dummy
    @PostMapping("/save")
    suspend fun saveAll() {
        for (i in 1..1000) {
            travelDestinationRepository.save(
                TravelDestination(
                    name = "a",
                    engName = "b",
                    code = "c",
                )
            )
        }
    }
}
