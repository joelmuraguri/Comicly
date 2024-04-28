package com.muraguri.comicly.core.domain.utils

// Sealed class representing different outcomes
sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure(val error: Throwable) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
}
