package com.muraguri.comicly.features.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen() {

    val tabItems = listOf("For You", "Favourites")

    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    val pagerState = rememberPagerState {
        tabItems.size
    }
    
    LaunchedEffect(key1 = selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }

    LaunchedEffect(key1 = pagerState, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress){
            selectedTabIndex = pagerState.currentPage
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
        ) {
            tabItems.forEachIndexed { index, title ->
                Tab(
                    text = {
                        Text(
                            text =  title,
                            modifier = Modifier
                                .padding(15.dp)
                        ) },
                    selected =  index == selectedTabIndex,
                    onClick = { selectedTabIndex = index }
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {index ->
            when(index){
                0 -> ForYouScreenContent()
                1-> FavouritesScreenContent()
            }

        }

    }
}

@Composable
fun ForYouScreenContent(){

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = "FOR YOU")
    }

}

@Composable
fun FavouritesScreenContent(){

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = "FAVOURITES")
    }

}