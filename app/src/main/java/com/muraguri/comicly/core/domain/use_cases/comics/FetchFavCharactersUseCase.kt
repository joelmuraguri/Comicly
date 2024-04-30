package com.muraguri.comicly.core.domain.use_cases.comics

import com.muraguri.comicly.core.domain.repo.ComicRepository
import com.muraguri.comicly.core.local.entity.FavCharacter
import kotlinx.coroutines.flow.Flow

class FetchFavCharactersUseCase(
    private val comicRepository: ComicRepository
) {

    operator fun invoke() : Flow<List<FavCharacter>> {
        return comicRepository.fetchFavouriteCharacters()
    }
}