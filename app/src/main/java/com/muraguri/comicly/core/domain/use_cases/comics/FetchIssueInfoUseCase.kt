package com.muraguri.comicly.core.domain.use_cases.comics

import com.muraguri.comicly.core.domain.models.comics.CharacterInfo
import com.muraguri.comicly.core.domain.models.comics.IssueInfo
import com.muraguri.comicly.core.domain.repo.ComicRepository
import com.muraguri.comicly.core.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

class FetchIssueInfoUseCase(
    private val repository: ComicRepository
) {

    suspend operator fun invoke(issueId: Int): Flow<Resource<IssueInfo>> {
        return repository.fetchIssuesInfo(issueId)
    }
}