package com.muraguri.comicly.features.preference.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchWidget(
    query : String,
    onQueryChange : (String) -> Unit,
    active : Boolean,
    onActiveChange : (Boolean) -> Unit,
    onSearch : () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
){


    val items = remember {
        mutableStateListOf(
            "Batman", "Superman", "Ironman", "Darkseid", "Steppenwolf", "Spider-man"
        )
    }

    SearchBar(
        query = query,
        onQueryChange = {
            onQueryChange(it)
        },
        onSearch = {
            onSearch()
        },
        active = active,
        onActiveChange = {
            onActiveChange(it)
        },
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        placeholder = {
            Text(text = "Search")
        },
        leadingIcon = {
            if (active) {
                IconButton(onClick = { onActiveChange(false) },
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            } else {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        },
        trailingIcon = {
            if (active && query.isNotEmpty()) {
                IconButton(
                        onClick = {
                            onQueryChange("")
                        }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary,)
                }
            }
        },
        colors = SearchBarDefaults.colors(
            containerColor = if (active) {
                MaterialTheme.colorScheme.background
            } else {
                MaterialTheme.colorScheme.surfaceContainerLow
            },
        ),
        content = content
    )
}