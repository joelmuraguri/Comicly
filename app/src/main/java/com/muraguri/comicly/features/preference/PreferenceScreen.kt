package com.muraguri.comicly.features.preference

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.muraguri.comicly.R
import com.muraguri.comicly.core.domain.models.comics.Character
import com.muraguri.comicly.core.domain.models.comics.Issue
import com.muraguri.comicly.di.ViewModelFactory
import com.muraguri.comicly.features.preference.components.SearchWidget
import com.muraguri.comicly.ui.connectivity.components.ErrorConnection
import com.muraguri.comicly.utils.trimTitle
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferenceScreen(
    viewModel: PreferenceViewModel = viewModel(factory = ViewModelFactory.Factory),
    onNavigate : () -> Unit
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
        },
        floatingActionButton = {
            Button(
                onClick = {
                    viewModel.onEvents(PreferenceEvent.OnUpdateFavCharacters)
                    if (state.selectedCharacters.isNotEmpty()){
                        onNavigate()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color(0xFF5180f1)
                ),
                modifier = Modifier
                    .padding(20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {
                    Text(
                        text = "Start",
                        modifier = Modifier
                    )
                    Icon(imageVector = Icons.Rounded.KeyboardArrowRight, contentDescription = null)
                }
            }
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
                        viewModel.onEvents(PreferenceEvent.OnSearch(state.query))
                    },
                    content = {
                        if (state.error.isNotEmpty()){
                            ErrorConnection(onRetry = { viewModel.getCharacters() }, message = state.error)
                        } else {
                            ScrollableItems(
                                pagingItems = searchResultsPagingItems,
                                onClick = {
                                    viewModel.onEvents(PreferenceEvent.OnUpdatedCharacter(it))
                                    viewModel.onEvents(PreferenceEvent.OnActiveChange(false))
                                    viewModel.onEvents(PreferenceEvent.OnQueryChange(""))
                                }
                            )
                        }
                    }
                )

                if (state.selectedCharacters.isEmpty()){
                    if (state.error.isNotEmpty()){
                        ErrorConnection(onRetry = { viewModel.getCharacters() }, message = state.error)
                    } else {
                        ScrollableItems(
                            pagingItems = charactersPagingItems,
                            onClick = {
                                viewModel.onEvents(PreferenceEvent.OnUpdatedCharacter(it))
                            }
                        )
                    }
                } else {
                    Column {
                        Text(
                            text = "Selected Characters",
                            fontSize = 22.sp,
                            fontFamily = FontFamily(Font(R.font.open_sans_medium)),
                            modifier = Modifier
                                .padding(start = 20.dp)
                        )
                        SelectedCharacters(
                            characters = state.selectedCharacters,
                            onClick = {
                                viewModel.onEvents(PreferenceEvent.OnUpdatedCharacter(it))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ScrollableItems(
    pagingItems: LazyPagingItems<Character>,
    onClick: (Character) -> Unit,
    landscape : Boolean = false
){

    LazyVerticalGrid(
        modifier = Modifier.padding(),
        columns = GridCells.Fixed(2)
    ) {
        items(pagingItems.itemCount){ index ->
            pagingItems[index]?.let { character ->
                CharacterItem(
                    character = character,
                    onClick = onClick,
                    isSelected = false,
                    modifier = Modifier
                        .width(if (landscape) 215.dp else 130.dp)
                        .height(if (landscape) 161.25.dp else 195.dp),
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
fun SelectedCharacters(
    characters : List<Character>,
    onClick: (Character) -> Unit,
    landscape: Boolean = false
){

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(characters.size){index ->
            val isSelected = characters.contains(characters[index])
            CharacterItem(
                character = characters[index],
                isSelected = isSelected,
                onClick = onClick,
                modifier = Modifier
                    .width(if (landscape) 215.dp else 130.dp)
                    .height(if (landscape) 161.25.dp else 195.dp),
            )
        }
    }

}


@Composable
fun CharacterCard(
    character: Character,
    isSelected : Boolean,
    onClick : (Character) -> Unit
) {

    Card(
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 5.dp
        ),
        modifier = Modifier
            .padding(12.dp)
            .clickable {
                onClick(character)
            }
            .fillMaxSize()
            .height(240.dp)
            .border(
                3.dp,
                if (isSelected) Color(0xFF5180f1) else Color.Transparent,
                RoundedCornerShape(8.dp)
            )
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


@Composable
fun CharacterItem(
    modifier: Modifier,
    landscape: Boolean = false,
    character: Character,
    isSelected : Boolean,
    onClick : (Character) -> Unit
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .padding(all = 4.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onClick(character)
            }
            .border(
                3.dp,
                if (isSelected) Color(0xFF5180f1) else Color.Transparent,
                RoundedCornerShape(8.dp)
            ),
        horizontalAlignment = Alignment.Start
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
                modifier = modifier.clip(RoundedCornerShape(8.dp)),
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


