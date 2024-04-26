package com.muraguri.comicly.navigation

import com.muraguri.comicly.R

sealed class Screens(val route: String, val icon: Int ?= null, val title: Int) {

    data object Home : Screens(
        route = "home_route",
        icon = R.drawable.round_home_24,
        title =  R.string.home_screen_title
    )
    data object Search : Screens(
        route = "search_route",
        icon = R.drawable.round_search_24,
        title =  R.string.search_screen_title
    )
    data object Settings : Screens(
        route = "settings_route",
        icon = R.drawable.round_settings_24,
        title = R.string.settings_screen_title
    )
    data object MyShelf : Screens(
        route = "my_shelf_route",
        icon = R.drawable.round_library_books_24,
        title =  R.string.my_shelf_screen_title
    )
    data object Explore : Screens(
        route = "explore_route",
        icon = R.drawable.round_explore_24,
        title = R.string.explore_screen_title
    )
    data object Notifications : Screens(
        route = "notifications_route",
        icon = R.drawable.round_notifications_24,
        title = R.string.notifications_screen_title
    )
    data object Profile : Screens(
        route = "profile_route",
        icon = R.drawable.round_person_24,
        title = R.string.profile_screen_title
    )

}


val bottomBarNavigationList = listOf(
    Screens.Home,
    Screens.Search,
    Screens.Profile,
)