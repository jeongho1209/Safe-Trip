package com.trip.safe.review.presentation

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class ReviewRouter {

    @Bean
    fun reviewBaseRouter(reviewHandler: ReviewHandler) = coRouter {
        "/reviews".nest {
            contentType(MediaType.APPLICATION_JSON)
            POST("", reviewHandler::createReview)
        }
    }
}
