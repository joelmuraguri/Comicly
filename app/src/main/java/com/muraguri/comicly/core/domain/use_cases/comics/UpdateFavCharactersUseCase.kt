package com.muraguri.comicly.core.domain.use_cases.comics

import com.muraguri.comicly.core.domain.repo.ComicRepository
import com.muraguri.comicly.core.local.entity.FavCharacter

class UpdateFavCharactersUseCase(
    private val comicRepository: ComicRepository
) {

    suspend operator fun invoke(characters: List<FavCharacter>) {
        return comicRepository.updateFavouriteCharacters(characters)
    }
}