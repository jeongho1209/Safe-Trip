package com.trip.safe.common.webclient.client

import com.trip.safe.common.logger.logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.util.DefaultUriBuilderFactory
import org.xml.sax.InputSource
import java.io.StringReader
import java.util.logging.Logger
import javax.xml.parsers.DocumentBuilderFactory

@Component
class CountrySafetyWebClient(
    @Value("\${service.key}")
    private val serviceKey: String,
    @Value("\${service.url}")
    private val serviceUrl: String,
) {
    companion object {
        private val TAG_LIST = listOf("content", "countryEnName", "countryName", "id", "title", "wrtDt")
    }

    private val log: Logger = logger()

    suspend fun getCountrySafetyInfo(searchId: String): MutableMap<String, String> {
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

        val documentBuilderFactory = DocumentBuilderFactory.newInstance()
        val documentBuilder = documentBuilderFactory.newDocumentBuilder()
        val document = withContext(Dispatchers.IO) {
            documentBuilder.parse(InputSource(StringReader(response)))
        }
        document.documentElement.normalize()

        val safetyInfoMap = mutableMapOf<String, String>()

        TAG_LIST.forEach { tagName ->
            safetyInfoMap[tagName] = document.getElementsByTagName(tagName).item(0).textContent
        }

        return safetyInfoMap
    }
}
