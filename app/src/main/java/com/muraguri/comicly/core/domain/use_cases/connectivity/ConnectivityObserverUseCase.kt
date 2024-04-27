package com.muraguri.comicly.core.domain.use_cases.connectivity

import com.muraguri.comicly.core.domain.repo.ConnectivityObserver

class ConnectivityObserverUseCase(
    private val connectivityObserver : ConnectivityObserver
){
    operator fun invoke() = connectivityObserver.observe()
}