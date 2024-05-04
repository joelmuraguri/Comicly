package com.muraguri.comicly.features.explore.character

import com.muraguri.comicly.core.domain.models.comics.CharacterInfo

data class CharacterInfoState(
    val isLoading: Boolean = false,
    val character: CharacterInfo? = null,
    val error: String = ""
)
