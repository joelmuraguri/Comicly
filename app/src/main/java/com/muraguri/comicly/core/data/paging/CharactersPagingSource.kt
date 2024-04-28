package com.muraguri.comicly.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.muraguri.comicly.core.data.mapping.CustomApiException
import com.muraguri.comicly.core.data.mapping.mapErrorCodeToException
import com.muraguri.comicly.core.data.mapping.toCharacterDomain
import com.muraguri.comicly.core.domain.models.comics.Character
import com.muraguri.comicly.core.domain.repo.ComicRemoteSource
import com.muraguri.comicly.core.remote.models.CharactersDTO
import retrofit2.HttpException
import java.io.IOException

class CharactersPagingSource(
    private val comicRemoteSource : ComicRemoteSource
) : PagingSource<Int, Character>(){

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
//        return state.anchorPosition
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val nextPage = params.key ?: 1
            val response =  comicRemoteSource.fetchCharacters()

            val nextKey = if (response.results.isEmpty()) null else nextPage + 1

            if (response.statusCode != 1) {
                val error = mapErrorCodeToException(response.statusCode)
                throw error
            }

            val mappedData = response.results.map { it.toCharacterDomain() }

            LoadResult.Page(
                data = mappedData,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = nextKey
//                prevKey = if (nextPage == 1) null else nextPage - 1,
//                nextKey = if (response.results.isEmpty()) null else response.offset + 1
            )

        } catch (e:CustomApiException){
            return LoadResult.Error(e)
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}