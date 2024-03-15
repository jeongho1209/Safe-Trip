package com.trip.safe

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class SafeTripServerApplication

fun main(args: Array<String>) {
    runApplication<SafeTripServerApplication>(*args)
}
