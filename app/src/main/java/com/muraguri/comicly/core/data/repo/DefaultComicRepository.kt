package com.muraguri.comicly.core.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import coil.network.HttpException
import com.muraguri.comicly.core.data.mapping.CustomApiException
import com.muraguri.comicly.core.data.mapping.mapErrorCodeToException
import com.muraguri.comicly.core.data.mapping.toIssueInfoDomain
import com.muraguri.comicly.core.data.paging.CharactersPagingSource
import com.muraguri.comicly.core.data.paging.IssuesPagingSource
import com.muraguri.comicly.core.data.paging.SearchPagingSource
import com.muraguri.comicly.core.domain.models.comics.Character
import com.muraguri.comicly.core.domain.models.comics.CharacterInfo
import com.muraguri.comicly.core.domain.models.comics.Issue
import com.muraguri.comicly.core.domain.models.comics.IssueInfo
import com.muraguri.comicly.core.domain.repo.ComicRemoteSource
import com.muraguri.comicly.core.domain.repo.ComicRepository
import com.muraguri.comicly.core.domain.utils.Resource
import com.muraguri.comicly.core.local.dao.FavouriteCharacterDao
import com.muraguri.comicly.core.local.entity.FavCharacter
import com.muraguri.comicly.core.remote.ComicsService
import com.muraguri.comicly.core.remote.DefaultComicRemoteSource
import com.muraguri.comicly.core.remote.models.IssueInfoDTO
import com.muraguri.comicly.core.remote.models.MovieInfoDTO
import com.muraguri.comicly.core.remote.models.PublisherInfoDTO
import com.muraguri.comicly.core.remote.models.TeamInfoDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.io.IOException

class DefaultComicRepository(
    private val remoteSource: ComicRemoteSource,
    private val favCharacterDao : FavouriteCharacterDao,
    private val service : ComicsService,
    private val apiKey : String
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
                Timber.d("CHARACTERS-PAGER", pagingDataFlow)
            } catch (e: CustomApiException) {
                emit(Resource.Failure(e))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }

    override suspend fun fetchIssues(): Flow<Resource<Flow<PagingData<Issue>>>> {
        return flow {
            try {
                emit(Resource.Loading)
                val pagingDataFlow = Pager(
                    config = PagingConfig(enablePlaceholders = false, pageSize = 20),
                    pagingSourceFactory = { IssuesPagingSource(comicRemoteSource = remoteSource) }
                ).flow
                emit(Resource.Success(pagingDataFlow))
                Timber.d("ISSUES-PAGER", pagingDataFlow)
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

    override suspend fun fetchCharacterInfo(characterId: Int): Flow<Resource<CharacterInfo>> = flow{
        try {
            emit(Resource.Loading)
            val response = remoteSource.fetchCharacterInfo(characterId)
            if (response.statusCode != 1) {
                throw mapErrorCodeToException(response.statusCode)
            }
            Timber.d("CHARACTER-INFO", response)
            val mappedData = DefaultComicRemoteSource(service, apiKey).mapCharacterInfo(response)
            Timber.d("CHARACTER-INFO", mappedData)
            emit(Resource.Success(mappedData))
        } catch (e : CustomApiException){
            emit(Resource.Failure(e))
        } catch (e : HttpException){
            emit(Resource.Failure(e))
        } catch (e : Exception){
            emit(Resource.Failure(e))
        }catch (e : IOException){
            emit(Resource.Failure(e))
        }
    }

    override suspend fun fetchIssuesInfo(issueId: Int): Flow<Resource<IssueInfo>> = flow {
        try {
            emit(Resource.Loading)
            val response = remoteSource.fetchIssuesInfo(issueId)
            if (response.statusCode != 1) {
                throw mapErrorCodeToException(response.statusCode)
            }
            Timber.d("ISSUE-INFO", response)
            val mappedData = response.results!!.toIssueInfoDomain()
            Timber.d("ISSUE-INFO", mappedData)
            emit(Resource.Success(mappedData))
        } catch (e : CustomApiException){
            emit(Resource.Failure(e))
        } catch (e : HttpException){
            emit(Resource.Failure(e))
        } catch (e : Exception){
            emit(Resource.Failure(e))
        }catch (e : IOException){
            emit(Resource.Failure(e))
        }
    }
    override suspend fun fetchPublisherInfo(publisherId: Int): PublisherInfoDTO {
        return remoteSource.fetchPublisherInfo(publisherId)
    }
    override suspend fun fetchMovieInfo(movieId: Int): MovieInfoDTO {
        return remoteSource.fetchMovieInfo(movieId)
    }
    override suspend fun fetchTeamInfo(teamId: Int): TeamInfoDTO {
        return remoteSource.fetchTeamInfo(teamId)
    }

    /****
     * Room Database(Local) logic
     * **/
    override fun fetchFavouriteCharacters(): Flow<List<FavCharacter>> {
        val response = favCharacterDao.fetchAllFavouriteCharacters()
        Timber.d("FAVS", response)
        return response
    }

    override suspend fun updateFavouriteCharacters(characters: List<FavCharacter>) {
        return favCharacterDao.insertCharacters(characters)
    }


}