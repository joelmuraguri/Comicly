package com.muraguri.comicly.core.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.muraguri.comicly.core.local.entity.FavCharacter
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteCharacterDao {

    @Query("SELECT * FROM Favourite_Characters_Table")
    fun fetchAllFavouriteCharacters() : Flow<List<FavCharacter>>

    @Upsert
    suspend fun insertCharacters(characters : List<FavCharacter>)

}