package com.muraguri.comicly.core.domain.use_cases

import com.muraguri.comicly.core.domain.use_cases.connectivity.ConnectivityObserverUseCase

data class CoreUseCases(
    val connectivityObserverUseCases: ConnectivityObserverUseCase
)
