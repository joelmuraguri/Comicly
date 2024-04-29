package com.muraguri.comicly.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.muraguri.comicly.features.home.HomeScreen
import com.muraguri.comicly.features.preference.PreferenceScreen
import com.muraguri.comicly.features.profile.ProfileScreen
import com.muraguri.comicly.features.search.SearchScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    updateBottomBarState: (Boolean) -> Unit,
    startDestination : String
){

    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        composable(route = Screens.Preference.route){
            updateBottomBarState(false)
            PreferenceScreen(
                onNavigate = {
                    navController.navigate(Screens.Home.route)
                }
            )
        }
        composable(route = Screens.Home.route){
            updateBottomBarState(true)
            HomeScreen()
        }
        composable(route = Screens.Search.route){
            updateBottomBarState(true)
            SearchScreen()
        }
        composable(route = Screens.Profile.route){
            updateBottomBarState(true)
            ProfileScreen()
        }
    }
}