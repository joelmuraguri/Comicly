package com.muraguri.comicly.core.domain.models.comics

data class Issue(
    val id : Int,
    val title : String,
    val image : String,
    val siteLink : String
)


data class IssueInfo(
    val id : Int,
    val name : String,
    val imageUrl : String,
    val deck : String,
)