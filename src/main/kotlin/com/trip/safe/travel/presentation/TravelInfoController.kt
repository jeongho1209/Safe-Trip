package com.trip.safe.travel.presentation

import com.trip.safe.travel.presentation.dto.request.CreateTravelInfoRequest
import com.trip.safe.travel.presentation.dto.request.UpdateTravelInfoRequest
import com.trip.safe.travel.presentation.dto.response.TravelInfoListResponse
import com.trip.safe.travel.service.TravelInfoService
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/travelInfo")
@RestController
class TravelInfoController(
    private val travelInfoService: TravelInfoService,
) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{travel-destination-id}")
    suspend fun createTravelInfo(
        @RequestBody @Valid request: CreateTravelInfoRequest,
        @PathVariable("travel-destination-id") travelDestinationId: Long,
    ) {
        travelInfoService.createTravelInfo(request, travelDestinationId)
    }

    @PatchMapping("/{travel-info-id}")
    suspend fun updateTravelInfo(
        @RequestBody @Valid request: UpdateTravelInfoRequest,
        @PathVariable("travel-info-id") travelInfoId: Long,
    ) {
        travelInfoService.updateTravelInfo(request, travelInfoId)
    }

    @DeleteMapping("/{travel-info-id}")
    suspend fun deleteTravelInfo(
        @PathVariable("travel-info-id") travelInfoId: Long,
    ) {
        travelInfoService.deleteTravelInfo(travelInfoId)
    }

    @GetMapping("/{travel-destination-id}")
    suspend fun getTravelInfosByTravelDestinationId(
        @PathVariable("travel-destination-id") travelDestinationId: Long,
        pageable: Pageable,
    ): TravelInfoListResponse {
        return travelInfoService.getTravelInfosByTravelDestinationId(travelDestinationId, pageable)
    }
}
