package com.muraguri.comicly.core.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieInfoDTO(
    val error: String,
    val limit: Int,
    @SerialName("number_of_page_results")
    val pageResults: Int,
    @SerialName("number_of_total_results")
    val totalResults: Int,
    val offset: Int,
    val results: Movie?= null,
    @SerialName("status_code")
    val statusCode: Int,
    val version: String
){

    @Serializable
    data class Movie(
        @SerialName("api_detail_url")
        val apiDetailUrl: String ?= null,
        @SerialName("box_office_revenue")
        val boxOfficeRevenue: String ?= null, //ANY
        val budget: String ?= null, //ANY
        val characters: List<Character> = emptyList(),
        val concepts: List<Concept> = emptyList(),
        @SerialName("date_added")
        val dateAdded: String ?= null,
        @SerialName("date_last_updated")
        val deck: String ?= null,
        val description: String ?= null,
        val distributor: String ?= null, //ANY
        @SerialName("has_staff_review")
        val hasStaffReview: String? = null, //ANY
        val id: Int ?= null,
        val image: Image,
        val locations: List<Location> = emptyList(),
        val name: String ?= null,
        val objects: List<Object> = emptyList(),
        val producers: List<Producer> = emptyList(),
        val rating: String ?= null,
        @SerialName("release_date")
        val releaseDate: String ?= null,
        val runtime: String ?= null,
        @SerialName("site_detail_url")
        val siteDetailUrl: String ?= null,
        val studios: List<Studio> = emptyList(),
        val teams: List<Team> = emptyList(),
        @SerialName("total_revenue")
        val totalRevenue: String ?= null,  // ANY
        val writers: List<Writers> = emptyList()  //ANY
    ) {

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
        data class Character(
            @SerialName("api_detail_url")
            val apiDetailUrl: String ?= null,
            val id: Int ?= null,
            val name: String ?= null,
            @SerialName("site_detail_url")
            val siteDetailUrl: String ?= null
        )

        @Serializable
        data class Writers(
            @SerialName("api_detail_url")
            val apiDetailUrl: String ?= null,
            val id: Int ?= null,
            val name: String ?= null,
            @SerialName("site_detail_url")
            val siteDetailUrl: String ?= null
        )

        @Serializable
        data class Concept(
            @SerialName("api_detail_url")
            val apiDetailUrl: String ?= null,
            val id: Int ?= null,
            val name: String ?= null,
            @SerialName("site_detail_url")
            val siteDetailUrl: String ?= null
        )

        @Serializable
        data class Location(
            @SerialName("api_detail_url")
            val apiDetailUrl: String ?= null,
            val id: Int ?= null,
            val name: String ?= null,
            @SerialName("site_detail_url")
            val siteDetailUrl: String ?= null
        )

        @Serializable
        data class Producer(
            @SerialName("api_detail_url")
            val apiDetailUrl: String ?= null,
            val id: Int ?= null,
            val name: String ?= null,
            @SerialName("site_detail_url")
            val siteDetailUrl: String ?= null
        )

        @Serializable
        data class Object(
            @SerialName("api_detail_url")
            val apiDetailUrl: String ?= null,
            val id: Int ?= null,
            val name: String ?= null,
            @SerialName("site_detail_url")
            val siteDetailUrl: String ?= null
        )

        @Serializable
        data class Team(
            @SerialName("api_detail_url")
            val apiDetailUrl: String ?= null,
            val id: Int ?= null,
            val name: String ?= null,
            @SerialName("site_detail_url")
            val siteDetailUrl: String ?= null
        )

        @Serializable
        data class Studio(
            @SerialName("api_detail_url")
            val apiDetailUrl: String ?= null,
            val id: Int ?= null,
            val name: String ?= null,
            @SerialName("site_detail_url")
            val siteDetailUrl: String ?= null
        )


    }
}