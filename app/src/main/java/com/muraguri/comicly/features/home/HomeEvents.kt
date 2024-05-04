package com.muraguri.comicly.features.home

import com.muraguri.comicly.core.domain.models.comics.Character
import com.muraguri.comicly.core.domain.models.comics.Issue
import com.muraguri.comicly.core.local.entity.FavCharacter

sealed class HomeEvents {

    data class OnCharacterClick(val character : FavCharacter) : HomeEvents()
    data class OnIssueClick(val issue : Issue) : HomeEvents()
}