package com.muraguri.comicly.features.preference

import androidx.paging.PagingData
import com.muraguri.comicly.core.domain.models.comics.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class PrefScreenState(
    val characters: Flow<PagingData<Character>> = emptyFlow(),
    val searchResults: Flow<PagingData<Character>> = emptyFlow(),
    val selectedCharacters: List<Character> = emptyList(),
    val loadingState: Boolean = false,
    val error: String = "",
    val query: String = ""
)
