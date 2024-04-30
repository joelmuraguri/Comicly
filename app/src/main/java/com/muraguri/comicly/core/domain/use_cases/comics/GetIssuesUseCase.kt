package com.muraguri.comicly.core.domain.use_cases.comics

import androidx.paging.PagingData
import com.muraguri.comicly.core.domain.models.comics.Issue
import com.muraguri.comicly.core.domain.repo.ComicRepository
import com.muraguri.comicly.core.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

class GetIssuesUseCase(
    private val comicRepository: ComicRepository
) {

    suspend operator fun invoke(): Flow<Resource<Flow<PagingData<Issue>>>> {
        return comicRepository.fetchIssues()
    }
}