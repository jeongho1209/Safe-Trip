package com.trip.safe.safeinfo.service

import com.trip.safe.common.webclient.client.CountrySafetyWebClient
import com.trip.safe.common.webclient.dto.response.CountrySafetyInfoElement
import com.trip.safe.safeinfo.domain.CountrySafeInfoRepository
import org.springframework.stereotype.Service

@Service
class SafeInfoService(
    private val countrySafeInfoRepository: CountrySafeInfoRepository,
    private val countrySafetyWebClient: CountrySafetyWebClient,
) {

    suspend fun getCountrySafetyInfo(searchId: String): CountrySafetyInfoElement {
        return countrySafetyWebClient.getCountrySafetyInfo(searchId)
    }
}
