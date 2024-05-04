package com.muraguri.comicly.core.domain.repo

import com.muraguri.comicly.core.remote.models.CharacterInfoDTO
import com.muraguri.comicly.core.remote.models.CharactersDTO
import com.muraguri.comicly.core.remote.models.IssueInfoDTO
import com.muraguri.comicly.core.remote.models.IssuesDTO
import com.muraguri.comicly.core.remote.models.MovieInfoDTO
import com.muraguri.comicly.core.remote.models.PublisherInfoDTO
import com.muraguri.comicly.core.remote.models.SearchDTO
import com.muraguri.comicly.core.remote.models.TeamInfoDTO

interface ComicRemoteSource {
    suspend fun fetchCharacters(offset : Int, limit :Int) : CharactersDTO
    suspend fun fetchCharacterInfo(characterId: Int) : CharacterInfoDTO
    suspend fun fetchIssuesInfo(issueId: Int) : IssueInfoDTO
    suspend fun fetchPublisherInfo(publisherId: Int) : PublisherInfoDTO
    suspend fun fetchMovieInfo(movieId: Int) : MovieInfoDTO
    suspend fun fetchTeamInfo(teamId: Int) : TeamInfoDTO
    suspend fun fetchIssues(offset : Int, limit :Int) : IssuesDTO
    suspend fun search(query : String,offset : Int, limit :Int) : SearchDTO
}