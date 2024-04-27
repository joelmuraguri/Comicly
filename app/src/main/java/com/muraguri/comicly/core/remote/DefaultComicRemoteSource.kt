package com.muraguri.comicly.core.remote

import com.muraguri.comicly.core.domain.repo.ComicRemoteSource
import com.muraguri.comicly.core.remote.ComicsService
import com.muraguri.comicly.core.remote.models.CharactersDTO
import com.muraguri.comicly.core.remote.models.SearchDTO

class DefaultComicRemoteSource(
    private val service: ComicsService,
    private val apiKey : String
) : ComicRemoteSource {

    override suspend fun fetchCharacters(): CharactersDTO {
        return service.fetchCharacters(
            apiKey = apiKey
        )
    }

    override suspend fun search(query: String): SearchDTO {
        return service.searchCharacter(
            apiKey = apiKey,
            query = query,
        )
    }

}