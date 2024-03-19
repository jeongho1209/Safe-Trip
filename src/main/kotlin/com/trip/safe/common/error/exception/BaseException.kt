package com.trip.safe.common.error.exception

interface ExceptionAttribute {
    val errorMessage: String
    val responseStatus: Int
}

abstract class BaseException(
    override val errorMessage: String,
    override val responseStatus: Int
) : RuntimeException(errorMessage), ExceptionAttribute {
    override fun fillInStackTrace(): Throwable = this
}
