package com.muraguri.comicly.core.domain.repo

import androidx.paging.PagingData
import com.muraguri.comicly.core.domain.models.comics.Character
import com.muraguri.comicly.core.domain.models.comics.CharacterInfo
import com.muraguri.comicly.core.domain.models.comics.Issue
import com.muraguri.comicly.core.domain.models.comics.IssueInfo
import com.muraguri.comicly.core.domain.utils.Resource
import com.muraguri.comicly.core.local.entity.FavCharacter
import com.muraguri.comicly.core.remote.models.IssueInfoDTO
import com.muraguri.comicly.core.remote.models.MovieInfoDTO
import com.muraguri.comicly.core.remote.models.PublisherInfoDTO
import com.muraguri.comicly.core.remote.models.TeamInfoDTO
import kotlinx.coroutines.flow.Flow

interface ComicRepository {
    suspend fun fetchCharacters() : Flow<Resource<Flow<PagingData<Character>>>>
    suspend fun fetchIssues() : Flow<Resource<Flow<PagingData<Issue>>>>
    suspend fun search(query : String) : Flow<Resource<Flow<PagingData<Character>>>>
    suspend fun fetchCharacterInfo(characterId: Int) : Flow<Resource<CharacterInfo>>
    suspend fun fetchIssuesInfo(issueId: Int) : Flow<Resource<IssueInfo>>
    suspend fun fetchPublisherInfo(publisherId: Int) : PublisherInfoDTO
    suspend fun fetchMovieInfo(movieId: Int) : MovieInfoDTO
    suspend fun fetchTeamInfo(teamId: Int) : TeamInfoDTO

    /**
     * localDatabase
     **/
    fun fetchFavouriteCharacters() : Flow<List<FavCharacter>>
    suspend fun updateFavouriteCharacters(characters : List<FavCharacter>)
}