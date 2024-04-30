package com.muraguri.comicly.core.remote

import com.muraguri.comicly.core.domain.repo.ComicRemoteSource
import com.muraguri.comicly.core.remote.ComicsService
import com.muraguri.comicly.core.remote.models.CharactersDTO
import com.muraguri.comicly.core.remote.models.IssuesDTO
import com.muraguri.comicly.core.remote.models.SearchDTO

class DefaultComicRemoteSource(
    private val service: ComicsService,
    private val apiKey : String
) : ComicRemoteSource {

    override suspend fun fetchCharacters(offset : Int, limit :Int): CharactersDTO {
        return service.fetchCharacters(
            apiKey = apiKey,
            offset = offset,
            limit = limit
        )
    }
    override suspend fun fetchIssues(offset: Int, limit: Int): IssuesDTO {
        return service.fetchIssues(
            apiKey = apiKey, offset = offset, limit = limit
        )
    }
    override suspend fun search(query: String,offset : Int, limit :Int): SearchDTO {
        return service.searchCharacter(
            apiKey = apiKey,
            query = query,
            offset = offset,
            limit = limit
        )
    }

}