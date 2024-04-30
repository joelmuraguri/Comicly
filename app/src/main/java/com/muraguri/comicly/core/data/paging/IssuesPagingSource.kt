package com.muraguri.comicly.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.muraguri.comicly.core.data.mapping.CustomApiException
import com.muraguri.comicly.core.data.mapping.mapErrorCodeToException
import com.muraguri.comicly.core.data.mapping.toIssuesDomain
import com.muraguri.comicly.core.domain.models.comics.Issue
import com.muraguri.comicly.core.domain.repo.ComicRemoteSource
import retrofit2.HttpException
import java.io.IOException

class IssuesPagingSource(
    private val comicRemoteSource: ComicRemoteSource
) : PagingSource<Int, Issue>() {

    override fun getRefreshKey(state: PagingState<Int, Issue>): Int? {
        return state.anchorPosition?.let { anchor ->
            val closestPage = state.closestPageToPosition(anchor)
            closestPage?.prevKey?.plus(1) ?: closestPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Issue> {
        val page = params.key ?: 0 // If no key is provided, default to page 0
        val offset = page * 20 // Offset increments by 100 with each page

        return try {
            val response = comicRemoteSource.fetchIssues(offset = offset, limit = 20)

            if (response.statusCode != 1) {
                throw mapErrorCodeToException(response.statusCode)
            }

            val mappedData = response.results.map { it.toIssuesDomain() }

            // Set prevKey and nextKey
            val prevKey = if (page == 0) null else page - 1
            val nextKey = if (mappedData.isEmpty() || response.totalResults <= offset + 20) null else page + 1

            LoadResult.Page(
                data = mappedData,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e : CustomApiException){
            LoadResult.Error(e)
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}