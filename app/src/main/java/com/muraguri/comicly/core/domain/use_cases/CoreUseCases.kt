package com.muraguri.comicly.core.domain.use_cases

import com.muraguri.comicly.core.domain.use_cases.comics.FetchCharacterInfoUseCase
import com.muraguri.comicly.core.domain.use_cases.comics.FetchFavCharactersUseCase
import com.muraguri.comicly.core.domain.use_cases.comics.GetCharactersUseCase
import com.muraguri.comicly.core.domain.use_cases.comics.GetIssuesUseCase
import com.muraguri.comicly.core.domain.use_cases.comics.SearchUseCase
import com.muraguri.comicly.core.domain.use_cases.comics.UpdateFavCharactersUseCase
import com.muraguri.comicly.core.domain.use_cases.connectivity.ConnectivityObserverUseCase

data class CoreUseCases(
    val connectivityObserverUseCases: ConnectivityObserverUseCase,
    val getCharactersUseCase: GetCharactersUseCase,
    val getIssuesUseCase: GetIssuesUseCase,
    val searchUseCase: SearchUseCase,
    val fetchFavCharactersUseCase: FetchFavCharactersUseCase,
    val updateFavCharactersUseCase: UpdateFavCharactersUseCase,
    val fetchCharacterInfoUseCase: FetchCharacterInfoUseCase
)
