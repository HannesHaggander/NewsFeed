package com.example.newsreader.helpers

import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.FlowCollector
import kotlin.coroutines.coroutineContext

suspend fun <T> FlowCollector<T>.safeEmit(value: T) {
    runCatching { coroutineContext.ensureActive() }.onSuccess { emit(value) }
}

inline fun <T> List<T>.onEmpty(action: () -> Unit): List<T> {
    if (isEmpty()) action.invoke()
    return this
}

inline fun <T> List<T>.onPopulated(action: (List<T>) -> Unit): List<T> {
    if (isNotEmpty()) action.invoke(this)
    return this
}