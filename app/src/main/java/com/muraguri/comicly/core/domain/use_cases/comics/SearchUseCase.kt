package com.muraguri.comicly.core.domain.use_cases.comics

import androidx.paging.PagingData
import com.muraguri.comicly.core.domain.models.comics.Character
import com.muraguri.comicly.core.domain.repo.ComicRepository
import com.muraguri.comicly.core.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

class SearchUseCase(
    private val comicRepository: ComicRepository
) {
    suspend operator fun invoke(query : String) : Flow<Resource<Flow<PagingData<Character>>>> {
        return comicRepository.search(query)
    }
}