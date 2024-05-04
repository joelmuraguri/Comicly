package com.muraguri.comicly.core.domain.use_cases.comics

import com.muraguri.comicly.core.domain.models.comics.CharacterInfo
import com.muraguri.comicly.core.domain.repo.ComicRepository
import com.muraguri.comicly.core.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

class FetchCharacterInfoUseCase(
    private val repository: ComicRepository
) {

    suspend operator fun invoke(characterId: Int): Flow<Resource<CharacterInfo>>{
        return repository.fetchCharacterInfo(characterId)
    }
}