package com.trip.safe.common.webclient.client

import com.trip.safe.common.error.exception.BadRequestException
import com.trip.safe.common.logger.logger
import com.trip.safe.common.webclient.dto.response.CountrySafetyInfoElement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.util.DefaultUriBuilderFactory
import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory

@Component
class CountrySafetyWebClient(
    @Value("\${service.key}")
    private val serviceKey: String,
    @Value("\${service.url}")
    private val serviceUrl: String,
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

    private val log: Logger = logger()

    suspend fun getCountrySafetyInfo(searchId: String): CountrySafetyInfoElement {
        val factory = DefaultUriBuilderFactory(serviceUrl).apply {
            encodingMode = DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY
        }

        val webClient = WebClient.builder()
            .uriBuilderFactory(factory)
            .filter { request, next ->
                log.info("try to request webclient url : ${request.url()}, method : ${request.method()}")
                next.exchange(request)
            }
            .build()

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
            countryEnName = safetyInfoMap[COUNTRY_EN_NAME]!!,
            countryName = safetyInfoMap[COUNTRY_NAME]!!,
            id = safetyInfoMap[ID]!!,
            title = safetyInfoMap[TITLE]!!,
            wrtDt = safetyInfoMap[WRT_DT]!!,
        )
    }

    suspend fun getCountrySafetyList(pageSize: Int, pageNumber: Int, title: String) {

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
