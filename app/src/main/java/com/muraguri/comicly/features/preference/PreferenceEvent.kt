package com.muraguri.comicly.features.preference

import com.muraguri.comicly.core.domain.models.comics.Character

sealed class PreferenceEvent {
    data class OnQueryChange(val query : String) : PreferenceEvent()
    data class OnActiveChange(val active : Boolean) : PreferenceEvent()
    data class OnSearch(val query: String) : PreferenceEvent()
    data class OnUpdatedCharacter(val character : Character) : PreferenceEvent()
}