package com.muraguri.comicly.core.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room
import com.muraguri.comicly.core.local.dao.FavouriteCharacterDao
import com.muraguri.comicly.core.local.entity.FavCharacter

@Database(
    entities = [FavCharacter::class],
    version = 1,
    exportSchema = false
)
abstract class ComiclyDatabase : RoomDatabase() {

    abstract fun favouriteCharacterDao() : FavouriteCharacterDao

    companion object {
        @Volatile
        private var Instance: ComiclyDatabase? = null

        fun getDatabase(context: Context): ComiclyDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, ComiclyDatabase::class.java, "comicly_database")
                    /**
                     * Setting this option in your app's database builder means that Room
                     * permanently deletes all data from the tables in your database when it
                     * attempts to perform a migration with no defined migration path.
                     */
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }

}