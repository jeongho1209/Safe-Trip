package com.trip.safe.travel.service

import com.trip.safe.common.error.exception.ForbiddenException
import com.trip.safe.common.security.SecurityFacade
import com.trip.safe.travel.domain.TravelDestinationRepository
import com.trip.safe.travel.domain.TravelInfo
import com.trip.safe.travel.domain.TravelInfoRepository
import com.trip.safe.travel.exception.TravelDestinationNotFoundException
import com.trip.safe.travel.exception.TravelInfoNotFoundException
import com.trip.safe.travel.presentation.dto.request.CreateTravelInfoRequest
import com.trip.safe.travel.presentation.dto.request.UpdateTravelInfoRequest
import com.trip.safe.travel.presentation.dto.response.TravelInfoListResponse
import com.trip.safe.travel.presentation.dto.response.toTravelInfoElement
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class TravelInfoService(
    private val travelInfoRepository: TravelInfoRepository,
    private val travelDestinationRepository: TravelDestinationRepository,
    private val securityFacade: SecurityFacade,
) {

    suspend fun createTravelInfo(request: CreateTravelInfoRequest, travelDestinationId: Long) {
        val user = securityFacade.getCurrentUser()

        val travelDestination = travelDestinationRepository.findById(travelDestinationId)
            ?: throw TravelDestinationNotFoundException(TravelDestinationNotFoundException.TRAVEL_DESTINATION_NOT_FOUND)

        travelInfoRepository.save(
            TravelInfo(
                title = request.title,
                content = request.content,
                createdDate = LocalDate.now(),
                travelDestinationId = travelDestination.id,
                userId = user.id
            )
        )
    }

    @Transactional
    suspend fun updateTravelInfo(request: UpdateTravelInfoRequest, travelInfoId: Long) {
        val user = securityFacade.getCurrentUser()

        val travelInfo = travelInfoRepository.findById(travelInfoId)
            ?: throw TravelInfoNotFoundException(TravelInfoNotFoundException.TRAVEL_INFO_NOT_FOUND)

        if (travelInfo.userId != user.id) {
            throw ForbiddenException(ForbiddenException.FORBIDDEN)
        }

        travelInfo.updateTravelInfo(
            title = request.title,
            content = request.content,
        )
        travelInfoRepository.save(travelInfo)
    }

    @Transactional
    suspend fun deleteTravelInfo(travelInfoId: Long) {
        val user = securityFacade.getCurrentUser()

        val travelInfo = travelInfoRepository.findById(travelInfoId)
            ?: throw TravelInfoNotFoundException(TravelInfoNotFoundException.TRAVEL_INFO_NOT_FOUND)

        if (travelInfo.userId != user.id) {
            throw ForbiddenException(ForbiddenException.FORBIDDEN)
        }

        travelInfo.deleteTravelInfo()
        travelInfoRepository.save(travelInfo)
    }

    suspend fun getTravelInfosByTravelDestinationId(
        travelDestinationId: Long,
        pageable: Pageable
    ): TravelInfoListResponse {
        val user = securityFacade.getCurrentUser()

        val travelInfoList = travelInfoRepository.findAllByTravelDestinationId(
            travelDestinationId = travelDestinationId,
            limit = pageable.pageSize,
            offset = pageable.offset,
        ).collectList().awaitSingle()

        val response = travelInfoList.map {
            val isMine = it.accountId == user.accountId
            it.toTravelInfoElement(isMine)
        }

        val travelDestination = travelDestinationRepository.findById(travelDestinationId)
            ?: throw TravelDestinationNotFoundException(TravelDestinationNotFoundException.TRAVEL_DESTINATION_NOT_FOUND)

        return TravelInfoListResponse(
            travelInfoList = response,
            travelDestination = travelDestination,
        )
    }
}
