package com.trip.safe.common.webclient.client

import com.trip.safe.common.error.exception.BadRequestException
import com.trip.safe.common.util.toLocalDate
import com.trip.safe.common.webclient.dto.response.CountrySafetyInfoElement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory

@Component
class CountrySafetyWebClient(
    @Value("\${service.key}")
    private val serviceKey: String,
    private val webClient: WebClient,
) {
    companion object {
        private const val CONTENT = "content"
        private const val COUNTRY_EN_NAME = "countryEnName"
        private const val COUNTRY_NAME = "countryName"
        private const val ID = "id"
        private const val TITLE = "title"
        private const val WRT_DT = "wrtDt"
        private val TAG_LIST = listOf(CONTENT, COUNTRY_EN_NAME, COUNTRY_NAME, ID, TITLE, WRT_DT)
    }

    suspend fun getCountrySafetyInfo(searchId: String): CountrySafetyInfoElement {
        val response = webClient
            .get()
            .uri { uriBuilder ->
                uriBuilder
                    .path("/getCountrySafetyInfo")
                    .queryParam("serviceKey", serviceKey)
                    .queryParam("id", searchId)
                    .build()
            }
            .retrieve()
            .awaitBody<String>()

        val safetyInfoMap = mutableMapOf<String, String>()
        parsingXML(response, safetyInfoMap)

        return CountrySafetyInfoElement(
            content = safetyInfoMap[CONTENT]!!,
            name = safetyInfoMap[COUNTRY_EN_NAME]!!,
            engName = safetyInfoMap[COUNTRY_NAME]!!,
            code = safetyInfoMap[ID]!!,
            title = safetyInfoMap[TITLE]!!,
            createdDate = safetyInfoMap[WRT_DT]!!.toLocalDate(),
        )
    }

    suspend fun getCountrySafetyList(
        pageSize: Int,
        pageNumber: Int,
        title: String,
        content: String?,
    ): String {
        val response = webClient
            .get()
            .uri { uriBuilder ->
                uriBuilder
                    .path("/getCountrySafetyList")
                    .queryParam("serviceKey", serviceKey)
                    .queryParam("numOfRows", pageSize)
                    .queryParam("pageNo", pageNumber)
                    .queryParam("title", title)
                    .queryParam("content", content)
                    .build()
            }
            .retrieve()
            .awaitBody<String>()

        return response
    }

    private suspend fun parsingXML(response: String, safetyInfoMap: MutableMap<String, String>) {
        val documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        val document = withContext(Dispatchers.IO) { documentBuilder.parse(InputSource(StringReader(response))) }
        runCatching {
            TAG_LIST.forEach { tagName ->
                safetyInfoMap[tagName] = document.getElementsByTagName(tagName).item(0).textContent
            }
        }.onFailure {
            throw BadRequestException(BadRequestException.BAD_REQUEST)
        }
    }
}
