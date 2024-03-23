package com.trip.safe.safeinfo.service

import com.trip.safe.common.webclient.client.CountrySafetyWebClient
import com.trip.safe.common.webclient.dto.response.CountrySafetyInfoElement
import com.trip.safe.safeinfo.domain.CountrySafeInfo
import com.trip.safe.safeinfo.domain.CountrySafeInfoRepository
import com.trip.safe.travel.domain.TravelDestination
import com.trip.safe.travel.domain.TravelDestinationRepository
import org.springframework.stereotype.Service

@Service
class SafeInfoService(
    private val countrySafeInfoRepository: CountrySafeInfoRepository,
    private val travelDestinationRepository: TravelDestinationRepository,
    private val countrySafetyWebClient: CountrySafetyWebClient,
) {

    suspend fun getCountrySafetyInfo(searchId: String): CountrySafetyInfoElement {
        travelDestinationRepository.findByCode(searchId)?.let {
            return CountrySafetyInfoElement(
                content = it.content,
                name = it.name,
                engName = it.engName,
                code = it.code,
                title = it.title,
                createdDate = it.createdDate,
            )
        }

        val countrySafetyInfo = countrySafetyWebClient.getCountrySafetyInfo(searchId)
        val newTravelDestination = travelDestinationRepository.save(
            TravelDestination(
                name = countrySafetyInfo.engName,
                engName = countrySafetyInfo.name,
                code = countrySafetyInfo.code // id == code
            )
        )

        countrySafeInfoRepository.save(
            CountrySafeInfo(
                title = countrySafetyInfo.title,
                content = countrySafetyInfo.content,
                createdDate = countrySafetyInfo.createdDate,
                travelDestinationId = newTravelDestination.id,
            )
        )

        return CountrySafetyInfoElement(
            content = countrySafetyInfo.content,
            name = countrySafetyInfo.name,
            engName = countrySafetyInfo.engName,
            code = countrySafetyInfo.code,
            title = countrySafetyInfo.title,
            createdDate = countrySafetyInfo.createdDate
        )
    }
}
