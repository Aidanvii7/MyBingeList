package com.aidanvii.mybingelist.core.redux

sealed class LoadingState {
    data class NotLoaded(val error: Throwable?) : LoadingState()
    object Loading : LoadingState()
    object Loaded : LoadingState()
}