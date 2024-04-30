package com.muraguri.comicly.features.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.muraguri.comicly.R
import com.muraguri.comicly.core.domain.models.comics.Issue
import com.muraguri.comicly.ui.connectivity.components.ErrorConnection
import timber.log.Timber

@Composable
fun ScrollableIssueItems(
    pagingItems: LazyPagingItems<Issue>,
    onClick: (Issue) -> Unit,
    landscape: Boolean = false
){


    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(pagingItems.itemCount){ index ->
            pagingItems[index]?.let { issues ->
                IssueItem(
                    landscape = landscape,
                    modifier = Modifier
                        .width(if (landscape) 215.dp else 130.dp)
                        .height(if (landscape) 161.25.dp else 195.dp),
                    onClick = {
                        onClick(issues)
                    },
                    issues = issues
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