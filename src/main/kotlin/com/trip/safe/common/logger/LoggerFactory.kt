package com.trip.safe.common.logger

import org.slf4j.LoggerFactory
import java.util.logging.Logger

inline fun <reified T> T.logger() = LoggerFactory.getLogger(T::class.java)!!
