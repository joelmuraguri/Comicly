package com.muraguri.comicly.core.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IssuesDTO(
    val error: String,
    val limit: Int,
    @SerialName("number_of_page_results")
    val pageResults: Int,
    @SerialName("number_of_total_results")
    val totalResults: Int,
    val offset: Int,
    val results: List<Issues> = emptyList(),
    @SerialName("status_code")
    val statusCode: Int,
    val version: String
) {
    @Serializable
    data class Issues(
        val aliases: String ?= null,
        @SerialName("api_detail_url")
        val apiDetailUrl: String ?= null,
        @SerialName("associated_images")
        val associatedImages: List<AssociatedImage> = emptyList(),
        @SerialName("cover_date")
        val coverDate: String ?= null,
        @SerialName("date_added")
        val dateAdded: String ?= null,
        @SerialName("date_last_updated")
        val dateLastUpdated: String ?= null,
        val deck: String ?= null,
        val description: String ?= null,
        @SerialName("has_staff_review")
        val hasStaffReview: Boolean = false,
        val id: Int ?= null,
        val image: Image,
        @SerialName("issue_number")
        val issueNumber: String ?= null,
        val name: String ?= null,
        @SerialName("site_detail_url")
        val siteDetailUrl: String ?= null,
        @SerialName("store_date")
        val storeDate: String ?= null,
        val volume: Volume
    ) {
        @Serializable
        data class AssociatedImage(
            val caption: String ?= null,
            val id: Int ?= null,
            @SerialName("image_tags")
            val imageTags: String ?= null,
            @SerialName("original_url")
            val originalUrl: String ?= null
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
        data class Volume(
            @SerialName("api_detail_url")
            val apiDetailUrl: String     ?= null,
            val id: Int ?= null,
            val name: String ?= null,
            @SerialName("site_detail_url")
            val siteDetailUrl: String ?= null
        )
    }
}