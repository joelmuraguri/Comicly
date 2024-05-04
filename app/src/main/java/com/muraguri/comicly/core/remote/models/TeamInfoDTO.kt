package com.muraguri.comicly.core.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TeamInfoDTO(
    val error: String,
    val limit: Int,
    @SerialName("number_of_page_results")
    val pageResults: Int,
    @SerialName("number_of_total_results")
    val totalResults: Int,
    val offset: Int,
    val results: Team?= null,
    @SerialName("status_code")
    val statusCode: Int,
    val version: String
){

    @Serializable
    data class Team(
        val aliases: String ?= null,
        @SerialName("api_detail_url")
        val apiDetailUrl: String ?= null,
        @SerialName("character_enemies")
        val characterEnemies: List<CharacterEnemy> = emptyList(),
        @SerialName("character_friends")
        val characterFriends: List<CharacterFriend> = emptyList(),
        val characters: List<Character>,
        @SerialName("count_of_issue_appearances")
        val countOfIssueAppearances: Int ?= null,
        @SerialName("count_of_team_members")
        val countOfTeamMembers: Int ?= null,
        @SerialName("date_added")
        val dateAdded: String ?= null,
        @SerialName("date_last_updated")
        val dateLastUpdated: String ?= null,
        val deck: String ?= null,
        val description: String ?= null,
        @SerialName("disbanded_in_issues")
        val disbandedInIssues: List<String> = emptyList(), //ANY
        @SerialName("first_appeared_in_issue")
        val firstAppearedInIssue: FirstAppearedInIssue?= null,
        val id: Int ?= null,
        val image: Image,
        @SerialName("isssues_disbanded_in")
        val issuesDisbandedIn: List<String> ?= emptyList(),  //ANY
        @SerialName("issue_credits")
        val issueCredits: List<IssueCredit> = emptyList(),
        val movies: List<Movie> = emptyList(),
        val name: String ?= null,
        val publisher: Publisher ?= null,
        @SerialName("site_detail_url")
        val siteDetailUrl: String ?= null,
        @SerialName("story_arc_credits")
        val storyArcCredits: List<StoryArcCredit>,
        @SerialName("volume_credits")
        val volumeCredits: List<VolumeCredit>
    ){

        @Serializable
        data class VolumeCredit(
            @SerialName("api_detail_url")
            val apiDetailUrl: String ?= null,
            val id: Int ?= null,
            val name: String ?= null,
            @SerialName("site_detail_url")
            val siteDetailUrl: String ?= null
        )

        @Serializable
        data class StoryArcCredit(
            @SerialName("api_detail_url")
            val apiDetailUrl: String ?= null,
            val id: Int ?= null,
            val name: String ?= null,
            @SerialName("site_detail_url")
            val siteDetailUrl: String ?= null
        )

        @Serializable
        data class Publisher(
            @SerialName("api_detail_url")
            val apiDetailUrl: String ?= null,
            val id: Int ?= null,
            val name: String ?= null
        )

        @Serializable
        data class Movie(
            @SerialName("api_detail_url")
            val apiDetailUrl: String ?= null,
            val id: Int ?= null,
            val name: String ?= null,
            @SerialName("site_detail_url")
            val siteDetailUrl: String ?= null
        )

        @Serializable
        data class FirstAppearedInIssue(
            @SerialName("api_detail_url")
            val apiDetailUrl: String ?= null,
            val id: Int ?= null,
            @SerialName("issue_number")
            val issueNumber: String ?= null,
            val name: String ?= null
        )

        @Serializable
        data class Image(
            @SerialName("icon_url")
            val icon: String? = null,
            @SerialName("image_tags")
            val imageTags: String ?= null,
            @SerialName("medium_url")
            val medium: String ?= null,
            @SerialName("original_url")
            val original: String ?= null,
            @SerialName("screen_large_url")
            val screenLarge: String ?= null,
            @SerialName("screen_url")
            val screen: String ?= null,
            @SerialName("small_url")
            val small: String ?= null,
            @SerialName("super_url")
            val superUrl: String ?= null,
            @SerialName("thumb_url")
            val thumb: String ?= null,
            @SerialName("tiny_url")
            val tiny: String ?= null
        )

        @Serializable
        data class IssueCredit(
            @SerialName("api_detail_url")
            val apiDetailUrl: String ?= null,
            val id: Int ?= null,
            val name: String ?= null,
            @SerialName("site_detail_url")
            val siteDetailUrl: String ?= null
        )

        @Serializable
        data class CharacterEnemy(
            @SerialName("api_detail_url")
            val apiDetailUrl: String ?= null,
            val id: Int ?= null,
            val name: String ?= null,
            @SerialName("site_detail_url")
            val siteDetailUrl: String ?= null
        )

        @Serializable
        data class CharacterFriend(
            @SerialName("api_detail_url")
            val apiDetailUrl: String ?= null,
            val id: Int ?= null,
            val name: String ?= null,
            @SerialName("site_detail_url")
            val siteDetailUrl: String ?= null
        )

        @Serializable
        data class Character(
            @SerialName("api_detail_url")
            val apiDetailUrl: String ?= null,
            val id: Int ?= null,
            val name: String ?= null,
            @SerialName("site_detail_url")
            val siteDetailUrl: String ?= null
        )
    }
}