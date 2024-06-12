package com.trip.safe.common.webclient.configuration

import com.trip.safe.common.logger.logger
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.DefaultUriBuilderFactory

@Configuration
class WebClientConfiguration(
    @Value("\${service.url}")
    private val serviceUrl: String,
) {
    private val log: Logger = logger()

    @Bean
    fun defaultUriFactory() = DefaultUriBuilderFactory(serviceUrl).apply {
        encodingMode = DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY
    }

    @Bean
    fun webClient() = WebClient.builder()
        .uriBuilderFactory(defaultUriFactory())
        .filter { request, next ->
            log.info("try to request webclient url : ${request.url()}")
            next.exchange(request)
        }
        .build()
}
