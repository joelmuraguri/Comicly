package com.muraguri.comicly.core.domain.models.comics

data class Character(
    val id : Int,
    val name : String,
    val alias : String,
    val image : String,
    val siteLink : String
)
