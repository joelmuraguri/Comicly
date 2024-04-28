package com.muraguri.comicly.core.domain.repo

import com.muraguri.comicly.core.remote.models.CharactersDTO
import com.muraguri.comicly.core.remote.models.SearchDTO

interface ComicRemoteSource {

//    suspend fun fetchStoryArcs()
//    suspend fun fetchIssues()
//    suspend fun fetchVolumes()

    suspend fun fetchCharacters(offset : Int, limit :Int) : CharactersDTO
    suspend fun search(query : String,offset : Int, limit :Int) : SearchDTO
}