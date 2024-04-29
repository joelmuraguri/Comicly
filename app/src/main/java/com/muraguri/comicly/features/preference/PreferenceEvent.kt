package com.muraguri.comicly.features.preference

sealed class PreferenceEvent {
    data class OnQueryChange(val query : String) : PreferenceEvent()
    data class OnActiveChange(val active : Boolean) : PreferenceEvent()
    data class OnSearch(val query: String) : PreferenceEvent()
}