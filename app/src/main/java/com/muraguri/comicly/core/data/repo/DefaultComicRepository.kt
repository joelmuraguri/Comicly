package com.muraguri.comicly.core.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.muraguri.comicly.core.data.mapping.CustomApiException
import com.muraguri.comicly.core.data.paging.CharactersPagingSource
import com.muraguri.comicly.core.data.paging.SearchPagingSource
import com.muraguri.comicly.core.domain.models.comics.Character
import com.muraguri.comicly.core.domain.repo.ComicRemoteSource
import com.muraguri.comicly.core.domain.repo.ComicRepository
import com.muraguri.comicly.core.domain.utils.Resource
import com.muraguri.comicly.core.local.dao.FavouriteCharacterDao
import com.muraguri.comicly.core.local.entity.FavCharacter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultComicRepository(
    private val remoteSource: ComicRemoteSource,
    private val favCharacterDao : FavouriteCharacterDao
) : ComicRepository {


    override suspend fun fetchCharacters(): Flow<Resource<Flow<PagingData<Character>>>> {
        return flow {
            try {
                emit(Resource.Loading)
                val pagingDataFlow = Pager(
                    config = PagingConfig(enablePlaceholders = false, pageSize = 20),
                    pagingSourceFactory = { CharactersPagingSource(comicRemoteSource = remoteSource) }
                ).flow
                emit(Resource.Success(pagingDataFlow))
            } catch (e: CustomApiException) {
                emit(Resource.Failure(e))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }

    override suspend fun search(query: String): Flow<Resource<Flow<PagingData<Character>>>> {
        return flow {
            try {
               emit(Resource.Loading)
               val pagingDataFlow = Pager(
                config = PagingConfig(enablePlaceholders = false, pageSize = 20),
                pagingSourceFactory = { SearchPagingSource(comicRemoteSource = remoteSource, query = query) }
            ).flow
            emit(Resource.Success(pagingDataFlow))
           } catch (e: CustomApiException) {
             emit(Resource.Failure(e))
           } catch (e: Exception) {
             emit(Resource.Failure(e))
          }
        }
    }

    /****
     * Room Database(Local) logic
     * **/
    override fun fetchFavouriteCharacters(): Flow<List<FavCharacter>> {
        return favCharacterDao.fetchAllFavouriteCharacters()
    }

    override suspend fun updateFavouriteCharacters(characters: List<FavCharacter>) {
        return favCharacterDao.insertCharacters(characters)
    }
}