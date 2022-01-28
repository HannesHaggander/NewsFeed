package com.example.newsreader

import androidx.annotation.StringRes

sealed class ViewState {
    data class Loading(
        @StringRes val loadingMessageRes: Int,
        val progressPercentage: Int? = 0
    ) : ViewState()

    data class Success<T>(val result: T) : ViewState()

    data class Error(val throwable: Throwable) : ViewState()
}
