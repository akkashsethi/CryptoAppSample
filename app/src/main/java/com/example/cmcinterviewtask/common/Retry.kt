package com.example.cmcinterviewtask.common

import kotlinx.coroutines.delay
import java.io.IOException

suspend fun <T> retryIO(
    times: Int,
    initialDelay: Long = 300,
    maxDelay: Long = 1000,
    factor: Double = 2.0,
    block: suspend () -> T
): T {
    var currentDelay = initialDelay
    repeat(times - 1) {
        try {
            return block()
        } catch (e: IOException) {
            delay(currentDelay)
            currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
        }
    }
    return block() // last attempt
}