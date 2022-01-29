package com.example.newsreader.helpers

import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.FlowCollector
import kotlin.coroutines.coroutineContext

suspend fun <T> FlowCollector<T>.safeEmit(value: T) {
    //runCatching { coroutineContext.ensureActive() }.onSuccess { emit(value) }
    emit(value)
}