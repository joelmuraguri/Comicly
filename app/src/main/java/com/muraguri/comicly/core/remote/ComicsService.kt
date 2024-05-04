package com.muraguri.comicly.core.remote

import com.muraguri.comicly.core.remote.models.CharacterInfoDTO
import com.muraguri.comicly.core.remote.models.CharactersDTO
import com.muraguri.comicly.core.remote.models.IssueInfoDTO
import com.muraguri.comicly.core.remote.models.IssuesDTO
import com.muraguri.comicly.core.remote.models.MovieInfoDTO
import com.muraguri.comicly.core.remote.models.PublisherInfoDTO
import com.muraguri.comicly.core.remote.models.SearchDTO
import com.muraguri.comicly.core.remote.models.TeamInfoDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ComicsService {

    @GET("characters")
    suspend fun fetchCharacters(
        @Query("api_key") apiKey : String,
        @Query("format") format: String = "json",
        @Query("sort") sort : String = "count_of_issue_appearances:desc",
        @Query("offset") offset : Int,
        @Query("limit") limit : Int
    ) : CharactersDTO

    @GET("character/{id}/")
    suspend fun fetchCharactersInfo(
        @Path("id") characterId : String,
        @Query("api_key") apiKey : String,
        @Query("format") format: String = "json",
    ) : CharacterInfoDTO

    @GET("issues")
    suspend fun fetchIssues(
        @Query("api_key") apiKey : String,
        @Query("format") format: String = "json",
        @Query("sort") sort : String = "date_last_updated:desc",
        @Query("offset") offset : Int,
        @Query("limit") limit : Int
    ) : IssuesDTO

    @GET("issue/{id}")
    suspend fun fetchIssuesInfo(
        @Path("id") issueId : String,
        @Query("api_key") apiKey : String,
        @Query("format") format: String = "json",
    ) : IssueInfoDTO

    @GET("publisher/{id}")
    suspend fun fetchPublisherInfo(
        @Path("id") publisherId : String,
        @Query("api_key") apiKey : String,
        @Query("format") format: String = "json",
    ) : PublisherInfoDTO

    @GET("movie/{id}")
    suspend fun fetchMovieInfo(
        @Path("id") movieId : String,
        @Query("api_key") apiKey : String,
        @Query("format") format: String = "json",
    ) : MovieInfoDTO

    @GET("team/{id}")
    suspend fun fetchTeamInfo(
        @Path("id") teamId : String,
        @Query("api_key") apiKey : String,
        @Query("format") format: String = "json",
    ) : TeamInfoDTO

    @GET("search")
    suspend fun searchCharacter(
        @Query("api_key") apiKey : String,
        @Query("format") format: String = "json",
        @Query("resources") resources: String = "character",
        @Query("query") query: String,
        @Query("offset") offset : Int,
        @Query("limit") limit : Int
    ) : SearchDTO



}