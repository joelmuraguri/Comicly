package com.muraguri.comicly.core.remote

import com.muraguri.comicly.core.remote.models.CharactersDTO
import com.muraguri.comicly.core.remote.models.SearchDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface ComicsService {


    @GET("characters/")
    suspend fun fetchCharacters(
        @Query("api_key") apiKey : String,
        @Query("format") format: String = "json",
        @Query("sort") sort : String = "count_of_issue_appearances"
    ) : CharactersDTO

    @GET("search")
    suspend fun searchCharacter(
        @Query("api_key") apiKey : String,
        @Query("format") format: String = "json",
        @Query("resources") resources: String = "character",
        @Query("query") query: String,
    ) : SearchDTO


//    @GET("/issues")
//    suspend fun fetchLatestIssues(
//        @Query("api_key") apiKey : String
//    )
//
//    @GET("issue/{id}")
//    suspend fun fetchIssuesDetailsById(
//        @Query("api_key") apiKey : String,
//        @Path("id") issueId : Int
//    )
//
//    @GET("character/{id}")
//    suspend fun fetchCharacterDetailsById(
//        @Query("api_key") apiKey : String,
//        @Path("id") characterId : Int
//    )
//
//    // Discover/Learn more
//    @GET("")
//    suspend fun fetchConcepts(
//        @Query("api_key") apiKey : String
//    )
//
//    @GET("/things")
//    suspend fun fetchThings(
//        @Query("api_key") apiKey : String
//    )
//
//    @GET("/locations")
//    suspend fun fetchLocations(
//        @Query("api_key") apiKey : String
//    )

}