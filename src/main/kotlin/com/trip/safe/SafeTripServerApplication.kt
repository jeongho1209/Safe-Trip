package com.trip.safe

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

internal const val BASE_PACKAGE = "com.trip.safe"

@SpringBootApplication
class SafeTripServerApplication

fun main(args: Array<String>) {
    runApplication<SafeTripServerApplication>(*args)
}
