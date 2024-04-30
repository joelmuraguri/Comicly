package com.muraguri.comicly.features.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.muraguri.comicly.R
import com.muraguri.comicly.core.domain.models.comics.Issue
import com.muraguri.comicly.core.local.entity.FavCharacter
import com.muraguri.comicly.di.ViewModelFactory
import com.muraguri.comicly.features.home.components.AddFavCharacterCarousel
import com.muraguri.comicly.features.home.components.FavCharacterIcon
import com.muraguri.comicly.features.home.components.ScrollableIssueItems
import com.muraguri.comicly.features.preference.PreferenceEvent
import com.muraguri.comicly.features.preference.ScrollableItems
import com.muraguri.comicly.ui.connectivity.components.ErrorConnection
import timber.log.Timber

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel(factory = ViewModelFactory.Factory)
) {

    val allFavCharacters by homeViewModel.favCharactersList.collectAsState()
    val tabItems = listOf("For You", "Favourites")
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState { tabItems.size }
    val state = homeViewModel.state.value
    val allNewComics = homeViewModel.state.value.allNewComics.collectAsLazyPagingItems()


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
        FavouritesCarousel(characters = allFavCharacters)
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {index ->
            when(index){
                0 -> ForYouScreenContent(
                    error = state.error,
                    pagingItems = allNewComics,
                    onClick = {},
                    onRetry = { homeViewModel.fetchNewComics()}
                )
                1-> FavouritesScreenContent()
            }

        }
    }
}

@Composable
fun FavouritesCarousel(
    characters : List<FavCharacter>
){

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(100.dp),
        contentPadding = PaddingValues(horizontal = 7.dp)
    ) {
        item {
            AddFavCharacterCarousel()
        }
        items(characters){character ->
            FavCharacterIcon(
                imageUrl = character.icon,
                imageSize = 80.dp,
                name = character.name
            )
        }
    }
}

@Composable
fun ForYouScreenContent(
    error : String,
    pagingItems: LazyPagingItems<Issue>,
    onClick: (Issue) -> Unit,
    onRetry : () -> Unit
){

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        item {
            Text(
                text = "New Comics",
                fontSize = 22.sp,
                fontFamily = FontFamily(Font(R.font.open_sans_semi_bold)),
                color = Color.White
            )
        }
        item {
            if (error.isNotEmpty()){
                ErrorConnection(onRetry = { onRetry() }, message = error)
            } else {
                ScrollableIssueItems(
                    pagingItems = pagingItems,
                    onClick = onClick,
                    landscape = false
                )
            }
        }
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

