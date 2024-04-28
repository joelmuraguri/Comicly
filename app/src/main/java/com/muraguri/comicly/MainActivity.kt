package com.muraguri.comicly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.muraguri.comicly.features.preference.PreferenceScreen
import com.muraguri.comicly.navigation.AppNavHost
import com.muraguri.comicly.navigation.BottomNavigationBar
import com.muraguri.comicly.navigation.Screens
import com.muraguri.comicly.ui.connectivity.ConnectivityObserverLayout
import com.muraguri.comicly.ui.theme.ComiclyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComiclyTheme {
                // A surface container using the 'background' color from the theme
                ConnectivityObserverLayout {
                    PreferenceScreen()
                }
            }
        }
    }
}


@Composable
fun ComiclyApp(startDestination : String){
    val navController = rememberNavController()
    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { if (bottomBarState.value) BottomNavigationBar(navController) },
        floatingActionButtonPosition = FabPosition.Center
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
        ) {
            AppNavHost(
                navController = navController,
                updateBottomBarState = { bottomBarState.value = it },
                startDestination = startDestination,
            )
        }
    }
}

