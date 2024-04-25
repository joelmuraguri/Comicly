package com.muraguri.comicly.core.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ComicsService {


    @GET("/issues")
    suspend fun fetchLatestIssues(
        @Query("api_key") apiKey : String
    )

    @GET("issue/{id}")
    suspend fun fetchIssuesDetailsById(
        @Query("api_key") apiKey : String,
        @Path("id") issueId : Int
    )

    @GET("")
    suspend fun fetchCharacters(
        @Query("api_key") apiKey : String
    )

    @GET("character/{id}")
    suspend fun fetchCharacterDetailsById(
        @Query("api_key") apiKey : String,
        @Path("id") characterId : Int
    )

    // Discover/Learn more
    @GET("")
    suspend fun fetchConcepts(
        @Query("api_key") apiKey : String
    )

    @GET("/things")
    suspend fun fetchThings(
        @Query("api_key") apiKey : String
    )

    @GET("/locations")
    suspend fun fetchLocations(
        @Query("api_key") apiKey : String
    )

}