package com.muraguri.comicly.core.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IssueInfoDTO(
    val error: String,
    val limit: Int,
    @SerialName("number_of_page_results")
    val pageResults: Int,
    @SerialName("number_of_total_results")
    val totalResults: Int,
    val offset: Int,
    val results: Issue?= null,
    @SerialName("status_code")
    val statusCode: Int,
    val version: String
){

    @Serializable
    data class Issue(
        val aliases: String ?= null,  //ANY
        @SerialName("api_detail_url")
        val apiDetailUrl: String ?= null,
        @SerialName("associated_images")
        val associatedImages: List<String> = emptyList(), // ANY
        @SerialName("character_credits")
        val characterCredits: List<CharacterCredit> = emptyList(),
        @SerialName("character_died_in")
        val characterDiedIn: List<String> = emptyList(),
        @SerialName("concept_credits")
        val conceptCredits: List<ConceptCredit> = emptyList(),
        @SerialName("cover_date")
        val coverDate: String ?= null,
        @SerialName("date_added")
        val dateAdded: String ?= null,
        @SerialName("date_last_updated")
        val dateLastUpdated: String ?= null,
        val deck: String ?= null, // ANY
        val description: String ?= null,
        @SerialName("first_appearance_characters")
        val firstAppearanceCharacters: String ?= null, //ANY\
        @SerialName("first_appearance_concepts")
        val firstAppearanceConcepts: String ?= null, //ANY
        @SerialName("first_appearance_locations")
        val firstAppearanceLocations: String ?= null, //ANY
        @SerialName("first_appearance_objects")
        val firstAppearanceObjects: String ?= null, //ANY
        @SerialName("first_appearance_storyarcs")
        val firstAppearanceStoryArcs: String ?= null, //ANY
        @SerialName("first_appearance_teams")
        val firstAppearanceTeams: String ?= null, //ANY
        @SerialName("has_staff_review")
        val hasStaffReview: Boolean = false,
        val id: Int ?= null,
        val image: Image,
        @SerialName("issue_number")
        val issueNumber: String ?= null,
        @SerialName("location_credits")
        val locationCredits: List<LocationCredit> = emptyList(),
        val name: String ?= null,
        @SerialName("object_credits")
        val objectCredits: List<String> = emptyList(), //ANY
        @SerialName("person_credits")
        val personCredits: List<PersonCredit> = emptyList(),
        @SerialName("site_detail_url")
        val siteDetailUrl: String ?= null,
        @SerialName("store_date")
        val storeDate: String ?= null,
        @SerialName("story_arc_credits")
        val storyArcCredits: List<String> = emptyList(), //ANY
        @SerialName("team_credits")
        val teamCredits: List<TeamCredit> = emptyList(),
        @SerialName("team_disbanded_in")
        val teamDisbandedIn: List<String> = emptyList(), //ANY
        val volume: Volume ?= null
    ){

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
        data class LocationCredit(
            @SerialName("api_detail_url")
            val apiDetailUrl: String ?= null,
            val id: Int ?= null,
            val name: String ?= null,
            @SerialName("site_detail_url")
            val siteDetailUrl: String ?= null
        )

        @Serializable
        data class PersonCredit(
            @SerialName("api_detail_url")
            val apiDetailUrl: String ?= null,
            val id: Int ?= null,
            val name: String ?= null,
            @SerialName("site_detail_url")
            val siteDetailUrl: String ?= null
        )

        @Serializable
        data class ConceptCredit(
            @SerialName("api_detail_url")
            val apiDetailUrl: String ?= null,
            val id: Int ?= null,
            val name: String ?= null,
            @SerialName("site_detail_url")
            val siteDetailUrl: String ?= null
        )

        @Serializable
        data class TeamCredit(
            @SerialName("api_detail_url")
            val apiDetailUrl: String ?= null,
            val id: Int ?= null,
            val name: String ?= null,
            @SerialName("site_detail_url")
            val siteDetailUrl: String ?= null
        )
        @Serializable
        data class CharacterCredit(
            @SerialName("api_detail_url")
            val apiDetailUrl: String ?= null,
            val id: Int ?= null,
            val name: String ?= null,
            @SerialName("site_detail_url")
            val siteDetailUrl: String ?= null
        )

        @Serializable
        data class Volume(
            @SerialName("api_detail_url")
            val apiDetailUrl: String ?= null,
            val id: Int ?= null,
            val name: String ?= null,
            @SerialName("site_detail_url")
            val siteDetailUrl: String ?= null
        )
    }
}