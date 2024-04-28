package com.muraguri.comicly.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.muraguri.comicly.core.data.mapping.toSearchDomain
import com.muraguri.comicly.core.domain.models.comics.Character
import com.muraguri.comicly.core.domain.repo.ComicRemoteSource
import retrofit2.HttpException
import java.io.IOException

class SearchPagingSource(
    private val comicRemoteSource : ComicRemoteSource,
    private val query : String
) : PagingSource<Int, Character>(){

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val nextPage = params.key ?: 1
            val characters =  comicRemoteSource.search(query)
            val response = characters.results.map { it.toSearchDomain() }

            LoadResult.Page(
                data = response,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (characters.results.isEmpty()) null else characters.offset + 1
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}