package com.muraguri.comicly.features.preference

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.muraguri.comicly.core.domain.models.comics.Character
import com.muraguri.comicly.di.ViewModelFactory
import com.muraguri.comicly.ui.connectivity.components.ErrorConnection

@Composable
fun PreferenceScreen(
    viewModel: PreferenceViewModel = viewModel(factory = ViewModelFactory.Factory)
){

    val charactersPagingItems = viewModel.state.value.characters.collectAsLazyPagingItems()
    val searchResultsPagingItems = viewModel.state.value.searchResults.collectAsLazyPagingItems()

    if (viewModel.state.value.error.isNotEmpty()){
        ErrorConnection(onRetry = { viewModel.getCharacters() }, message = viewModel.state.value.error)
    } else {
        ScrollableItems(pagingItems = charactersPagingItems)

    }

}

@Composable
fun ScrollableItems(
    pagingItems: LazyPagingItems<Character>
){

    LazyColumn(
        modifier = Modifier.padding()
    ) {
        items(pagingItems.itemCount) { index ->
            CharacterCard(character = pagingItems[index]!!)
        }
        pagingItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                loadState.refresh is LoadState.Error -> {
                    val error = pagingItems.loadState.refresh as LoadState.Error
                    item {
                        ErrorConnection(
                            message = error.error.localizedMessage!!,
                            onRetry = {
                                retry()
                            }
                        )
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item {
                        CircularProgressIndicator(modifier = Modifier)
                    }
                }

                loadState.append is LoadState.Error -> {
                    val error = pagingItems.loadState.append as LoadState.Error
                    item {
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


@Composable
fun CharacterCard(character: Character) {
    // Define how each character should be displayed
    Text("Character: ${character.name}")
}
