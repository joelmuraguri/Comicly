package com.muraguri.comicly.core.domain.use_cases.comics

import com.muraguri.comicly.core.domain.repo.ComicRepository

class SearchUseCase(
    private val comicRepository: ComicRepository
) {
    suspend operator fun invoke(query : String) = comicRepository.search(query)
}