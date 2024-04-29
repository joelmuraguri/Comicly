package com.muraguri.comicly.core.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Favourite_Characters_Table")
data class FavCharacter(
    @PrimaryKey val id : Int,
    val image : String,
    val name : String,
    val icon : String
)
