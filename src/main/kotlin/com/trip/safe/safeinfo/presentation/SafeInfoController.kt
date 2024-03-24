package com.trip.safe.safeinfo.presentation

import com.trip.safe.common.webclient.client.CountrySafetyWebClient
import com.trip.safe.common.webclient.dto.response.CountrySafetyInfoElement
import com.trip.safe.common.webclient.dto.response.CountrySafetyInfoList
import com.trip.safe.safeinfo.service.SafeInfoService
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/safeInfo")
@RestController
class SafeInfoController(
    private val safeInfoService: SafeInfoService,
    private val countrySafetyWebClient: CountrySafetyWebClient, // TODO: 수정 필요
) {

    @GetMapping
    suspend fun getCountrySafetyInfo(
        @RequestParam("searchId") searchId: String
    ): CountrySafetyInfoElement {
        return safeInfoService.getCountrySafetyInfo(searchId)
    }

    @GetMapping("/list")
    suspend fun getCountrySafetyListByNameOrEngName(
        @RequestParam("name", required = false) name: String?,
        @RequestParam("engName", required = false) engName: String?,
        pageable: Pageable,
    ): CountrySafetyInfoList {
        return safeInfoService.getCountrySafetyListByNameOrEngName(
            name, engName, pageable
        )
    }

    //    @GetMapping("/list")
    suspend fun getCountrySafetyList(
        @RequestParam("size", defaultValue = "5") pageSize: Int,
        @RequestParam("pageNumber", defaultValue = "1") pageNumber: Int,
        @RequestParam("title") title: String,
        @RequestParam("content", required = false) content: String?,
    ): String { // TODO: 수정 필요
        return countrySafetyWebClient.getCountrySafetyList(
            pageSize, pageNumber, title, content
        )
    }
}
