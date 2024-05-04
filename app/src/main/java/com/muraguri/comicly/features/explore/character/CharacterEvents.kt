package com.muraguri.comicly.features.explore.character

import com.muraguri.comicly.core.domain.models.comics.CharacterInfo
import com.muraguri.comicly.core.domain.utils.Resource

sealed class CharacterEvents {
    data class GetSelectedCharacter(val id : Int) : CharacterEvents()
    data class UpdateCharacterInfoScreen(val character: Resource<CharacterInfo?>) : CharacterEvents()
}