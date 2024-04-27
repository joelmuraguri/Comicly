package com.muraguri.comicly.core.remote.repo

import com.muraguri.comicly.core.remote.models.CharactersDTO
import com.muraguri.comicly.core.remote.models.SearchDTO

interface ComicRemoteSource {

//    suspend fun fetchStoryArcs()
//    suspend fun fetchIssues()
//    suspend fun fetchVolumes()

    suspend fun fetchCharacters() : CharactersDTO
    suspend fun search(query : String) : SearchDTO
}