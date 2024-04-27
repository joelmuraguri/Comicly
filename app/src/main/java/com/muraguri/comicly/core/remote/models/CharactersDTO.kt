package com.muraguri.comicly.core.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharactersDTO(
    val error: String,
    val limit: Int,
    @SerialName("number_of_page_results")
    val pageResults: Int,
    @SerialName("number_of_total_results")
    val totalResults: Int,
    val offset: Int,
    val results: List<Character>,
    @SerialName("status_code")
    val statusCode: Int,
    val version: String
){

    @Serializable
    data class Character(
        val aliases: String ?= null,
        @SerialName("api_detail_url")
        val apiDetailUrl: String ?= null,
        val birth: String ?= null,
        @SerialName("count_of_issue_appearances")
        val countOfIssueAppearances: Int ?= null,
        @SerialName("date_added")
        val dateAdded: String ?= null,
        @SerialName("date_last_updated")
        val dateLastUpdated: String ?= null,
        val deck: String ?= null,
        val description: String ?= null,
        @SerialName("first_appeared_in_issue")
        val firstAppearedInIssue: FirstAppearedInIssue,
        val gender: Int ?= null,
        val id: Int ?= null,
        val image: Image,
        val name: String,
        val origin: Origin,
        val publisher: Publisher,
        @SerialName("real_name")
        val realName: String ?= null,
        @SerialName("site_detail_url")
        val siteDetailUrl: String ?= null
    ) {

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
        data class Origin(
            @SerialName("api_detail_url")
            val apiDetailUrl: String ?= null,
            val id: Int ?= null,
            val name: String ?= null
        )

        @Serializable
        data class Publisher(
            @SerialName("api_detail_url")
            val apiDetailUrl: String ?= null,
            val id: Int ?= null,
            val name: String ?= null
        )
    }
}