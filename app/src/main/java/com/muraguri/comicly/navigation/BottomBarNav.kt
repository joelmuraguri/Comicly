package com.muraguri.comicly.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.muraguri.comicly.ui.theme.ComiclyTheme

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    BottomAppBar() {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        bottomBarNavigationList.forEach { destination ->
            val selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true
            NavigationBarItem(
                selected = selected,
                icon = {
                    Box(
                        modifier = Modifier.size(37.dp),
//                            .background(
////                                color = if (selected) Color(0xFF00B969) else Color.Transparent,
//                                shape = RoundedCornerShape(10.dp)
//                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = destination.icon!!),
                            contentDescription = stringResource(id = destination.title),
                            tint = if (selected) Color(0xFF5180f1) else Color.White
                        )
                    }
                },
                label = {  },
                alwaysShowLabel = true,
                onClick = {
                    navController.navigate(destination.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(Screens.Home.route){
                            inclusive = true
                            saveState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color(0xFF00B969),
                    selectedTextColor = Color(0xFF00B969),
                    unselectedTextColor = Color(0xFF00B969),
                    indicatorColor = Color.White
                )
            )
        }
    }
}

@Preview
@Composable
fun BottomNavigationBarPreview() {
    ComiclyTheme {
        BottomNavigationBar(rememberNavController())
    }
}