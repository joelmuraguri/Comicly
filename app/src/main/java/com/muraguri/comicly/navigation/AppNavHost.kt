package com.muraguri.comicly.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.muraguri.comicly.di.ViewModelFactory
import com.muraguri.comicly.features.explore.character.CharacterEvents
import com.muraguri.comicly.features.explore.character.CharacterInfoScreen
import com.muraguri.comicly.features.explore.character.CharacterViewModel
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
            HomeScreen(
                onNavigate = { events ->
                    navController.navigate(events.route)
                }
            )
        }
        composable(
            route = Screens.CharacterInfo.route  + "?characterId={${CHARACTER_ARGUMENT_KEY}}",
            arguments = listOf(navArgument(CHARACTER_ARGUMENT_KEY){
                type = NavType.IntType
                defaultValue = -1
            })
        ){ navBackStackEntry ->
            updateBottomBarState(false)
            val factory = ViewModelFactory.Factory
            val characterViewModel: CharacterViewModel = viewModel(factory = factory)
            val characterId =  navBackStackEntry.arguments!!.getInt(CHARACTER_ARGUMENT_KEY)

            LaunchedEffect(key1 = characterId){
                characterViewModel.onEvents(CharacterEvents.GetSelectedCharacter(characterId))
            }

//            val selectedCharacter by characterViewModel.selectedCharacter.collectAsState()
//            LaunchedEffect(key1 = selectedCharacter){
//                characterViewModel.onEvents(CharacterEvents.UpdateCharacterInfoScreen(character = selectedCharacter))
//            }
            CharacterInfoScreen()
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