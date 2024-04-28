package com.muraguri.comicly.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.muraguri.comicly.core.data.mapping.CustomApiException
import com.muraguri.comicly.core.data.mapping.mapErrorCodeToException
import com.muraguri.comicly.core.data.mapping.toCharacterDomain
import com.muraguri.comicly.core.domain.models.comics.Character
import com.muraguri.comicly.core.domain.repo.ComicRemoteSource
import retrofit2.HttpException
import java.io.IOException

const val CHARACTER_PAGE_SIZE = 100
private const val INITIAL_LOAD_SIZE = 0


const val LIMIT = 100 // The number of items per page

class CharactersPagingSource(
    private val comicRemoteSource: ComicRemoteSource
) : PagingSource<Int, Character>() {

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchor ->
            val closestPage = state.closestPageToPosition(anchor)
            closestPage?.prevKey?.plus(1) ?: closestPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val page = params.key ?: 0 // If no key is provided, default to page 0
        val offset = page * LIMIT // Offset increments by 100 with each page

        return try {
            val response = comicRemoteSource.fetchCharacters(offset = offset, limit = LIMIT)

            if (response.statusCode != 1) {
                throw mapErrorCodeToException(response.statusCode)
            }

            val mappedData = response.results.map { it.toCharacterDomain() }

            // Set prevKey and nextKey
            val prevKey = if (page == 0) null else page - 1
            val nextKey = if (mappedData.isEmpty() || response.totalResults <= offset + LIMIT) null else page + 1

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

