package com.muraguri.comicly.utils

sealed class ComiclyEvents {
    data class Navigate(val route : String) : ComiclyEvents()
    data object PopBackStack : ComiclyEvents()
    data class SnackBar(val message : String, val action : String?= null) : ComiclyEvents()
}