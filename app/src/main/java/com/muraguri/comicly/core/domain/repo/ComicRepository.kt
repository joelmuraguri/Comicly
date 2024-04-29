package com.muraguri.comicly.core.domain.repo

import androidx.paging.PagingData
import com.muraguri.comicly.core.domain.models.comics.Character
import com.muraguri.comicly.core.domain.utils.Resource
import com.muraguri.comicly.core.local.entity.FavCharacter
import kotlinx.coroutines.flow.Flow

interface ComicRepository {
    suspend fun fetchCharacters() : Flow<Resource<Flow<PagingData<Character>>>>
    suspend fun search(query : String) : Flow<Resource<Flow<PagingData<Character>>>>

    /**
     * localDatabase
     **/
    fun fetchFavouriteCharacters() : Flow<List<FavCharacter>>
    suspend fun updateFavouriteCharacters(characters : List<FavCharacter>)
}