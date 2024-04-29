package com.muraguri.comicly.features.preference

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.muraguri.comicly.R
import com.muraguri.comicly.core.domain.models.comics.Character
import com.muraguri.comicly.di.ViewModelFactory
import com.muraguri.comicly.features.preference.components.SearchWidget
import com.muraguri.comicly.ui.connectivity.components.ErrorConnection
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferenceScreen(
    viewModel: PreferenceViewModel = viewModel(factory = ViewModelFactory.Factory)
){

    val charactersPagingItems = viewModel.state.value.characters.collectAsLazyPagingItems()
    val searchResultsPagingItems = viewModel.state.value.searchResults.collectAsLazyPagingItems()
    val state = viewModel.state.value
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = "Choose Your Favourite Characters",
                        fontSize = 26.sp,
                        fontFamily = FontFamily(Font(R.font.open_sans_semi_bold))
                    ) 
                }
            )
        }
    ) { paddingValues ->
        
        Box(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
            ) {
                SearchWidget(
                    query = state.query,
                    onQueryChange = {
                        viewModel.onEvents(PreferenceEvent.OnQueryChange(it))
                    },
                    active = state.active,
                    onActiveChange = {
                        viewModel.onEvents(PreferenceEvent.OnActiveChange(it))
                    },
                    onSearch = {
//                        viewModel.onEvents(PreferenceEvent.OnActiveChange(false))
//                        viewModel.onEvents(PreferenceEvent.OnQueryChange(""))
                        viewModel.onEvents(PreferenceEvent.OnSearch(state.query))
                    },
                    content = {
                        if (state.error.isNotEmpty()){
                            ErrorConnection(onRetry = { viewModel.getCharacters() }, message = state.error)
                        } else {
                            ScrollableItems(pagingItems = searchResultsPagingItems)
                        }
                    }
                )
                if (state.error.isNotEmpty()){
                    ErrorConnection(onRetry = { viewModel.getCharacters() }, message = state.error)
                } else {
                    ScrollableItems(pagingItems = charactersPagingItems)
                } 
            }
        }
    }
}

@Composable
fun ScrollableItems(
    pagingItems: LazyPagingItems<Character>
){

    LazyVerticalGrid(
        modifier = Modifier.padding(),
        columns = GridCells.Fixed(2)
    ) {
        items(pagingItems.itemCount){ index ->
            pagingItems[index]?.let { character ->
                CharacterCard(
                    character = character,
                )
            }
        }
        pagingItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                loadState.refresh is LoadState.Error -> {
                    val error = pagingItems.loadState.refresh as LoadState.Error
                    item {
                        Box(
                            contentAlignment = Alignment.Center,
                        ) {
                            ErrorConnection(
                                message = error.error.localizedMessage!!,
                                onRetry = {
                                    retry()
                                }
                            )
                        }
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                loadState.append is LoadState.Error -> {
                    val error = pagingItems.loadState.append as LoadState.Error
                    item {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            ErrorConnection(
                                message = error.error.localizedMessage!!,
                                onRetry = {
                                    retry()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun CharacterCard(character: Character) {


    Card(
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 5.dp
        ),
        modifier = Modifier
            .padding(12.dp)
            .clickable {
            }
            .fillMaxSize()
            .height(240.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CoilImage(
                imageModel = character.image,
                shimmerParams = ShimmerParams(
                    baseColor = Color(0xFF180E36),
                    highlightColor = Color(0XFF423460),
                    durationMillis = 500,
                    dropOff = 0.65F,
                    tilt = 20F
                ),
                failure = {
                },
                contentScale = ContentScale.Crop,
                circularReveal = CircularReveal(duration = 1000),
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(8.dp)),
                contentDescription = "Movie item"
            )

            Text(
                text = character.name,
                color = Color.White,
                modifier = Modifier
                    .background(Color(0x80000000))
                    .padding(8.dp),
                fontWeight = FontWeight.ExtraBold
            )
        }
    }

}
