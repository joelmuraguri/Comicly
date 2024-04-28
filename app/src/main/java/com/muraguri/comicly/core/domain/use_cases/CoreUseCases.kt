package com.muraguri.comicly.core.domain.use_cases

import com.muraguri.comicly.core.domain.use_cases.comics.GetCharactersUseCase
import com.muraguri.comicly.core.domain.use_cases.comics.SearchUseCase
import com.muraguri.comicly.core.domain.use_cases.connectivity.ConnectivityObserverUseCase

data class CoreUseCases(
    val connectivityObserverUseCases: ConnectivityObserverUseCase,
    val getCharactersUseCase: GetCharactersUseCase,
    val searchUseCase: SearchUseCase
)
