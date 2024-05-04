package com.muraguri.comicly.core.domain.models.comics

data class Character(
    val id : Int,
    val name : String,
    val alias : String,
    val image : String,
    val siteLink : String,
    val icon : String
)

data class CharacterInfo(
    val id : Int,
    val name : String,
    val realName : String,
    val deck : String,
    val overView : String,
    val origin : String,
    val siteLink: String,
    val image: String,
    val issueAppearance : Int,
    val powers : List<Power>,
    val movies : List<Movie> = emptyList(),
    val issues : List<Issues> = emptyList(),
    val allies : List<Allies> = emptyList(),
    val nemesis : List<Nemesis> = emptyList(),
    val teamAllies : List<AlliesTeam> = emptyList(),
    val teamNemesis : List<NemesisTeam> = emptyList(),
    val teams : List<Team> = emptyList(),
    val publisher : Publisher
){
    data class Movie(
        val id : Int,
        val name : String,
        val siteLink : String,
        val image : String
    )

    data class Power(
        val id :Int,
        val name : String,
        val siteLink : String
    )

    data class Issues(
        val id : Int,
        val title : String,
        val image : String,
        val siteLink : String
    )

    data class Allies(
        val id : Int,
        val title : String,
        val image : String,
        val siteLink : String
    )
    data class Nemesis(
        val id : Int,
        val title : String,
        val image : String,
        val siteLink : String
    )
    data class AlliesTeam(
        val id : Int,
        val title : String,
        val image : String,
        val siteLink : String
    )
    data class NemesisTeam(
        val id : Int,
        val title : String,
        val image : String,
        val siteLink : String
    )
    data class Team(
        val id : Int,
        val title : String,
        val image : String,
        val siteLink : String
    )

    data class Publisher(
        val id : Int,
        val title : String,
        val image : String,
        val siteLink : String
    )

}
