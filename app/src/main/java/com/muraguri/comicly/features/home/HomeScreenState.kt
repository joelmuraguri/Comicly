package com.muraguri.comicly.features.home

import androidx.paging.PagingData
import com.muraguri.comicly.core.domain.models.comics.Issue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class HomeScreenState(
    val allNewComics : Flow<PagingData<Issue>> = emptyFlow(),
    val isLoading : Boolean = false,
    val error : String = ""
)
