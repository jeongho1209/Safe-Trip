package com.trip.safe.safeinfo.presentation

import com.trip.safe.common.webclient.dto.response.CountrySafetyInfoElement
import com.trip.safe.safeinfo.service.SafeInfoService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/safeInfo")
@RestController
class SafeInfoController(
    private val safeInfoService: SafeInfoService,
) {

    @GetMapping
    suspend fun getCountrySafetyInfo(
        @RequestParam("searchId") searchId: String
    ): CountrySafetyInfoElement {
        return safeInfoService.getCountrySafetyInfo(searchId)
    }
}
