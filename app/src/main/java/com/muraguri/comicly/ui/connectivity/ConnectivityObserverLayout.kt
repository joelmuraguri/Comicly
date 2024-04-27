package com.muraguri.comicly.ui.connectivity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.muraguri.comicly.core.domain.models.utils.ConnectivityStatus
import com.muraguri.comicly.ui.connectivity.utils.ConnectivityObserverEvent
import com.muraguri.comicly.ui.theme.ComiclyTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.muraguri.comicly.di.ViewModelFactory
import com.muraguri.comicly.ui.connectivity.components.NetworkUnavailable

@Composable
fun ConnectivityObserverLayout(
    viewModel: ConnectivityObserverViewModel = viewModel(factory = ViewModelFactory.Factory),
    content: @Composable () -> Unit
) {
    ConnectivityObserverLayoutContent(
        status = viewModel.state.status,
        onDismissNetwork = {
            viewModel.onEvent(ConnectivityObserverEvent.OnDismissNetworkError)
        },
        content = content
    )
}

@Composable
fun ConnectivityObserverLayoutContent(
    status: ConnectivityStatus,
    onDismissNetwork: () -> Unit,
    content: @Composable () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        content()
        when (status) {
            ConnectivityStatus.LOSING,
            ConnectivityStatus.LOST,
            ConnectivityStatus.UNAVAILABLE -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    NetworkUnavailable(
                        onDismissRequest = onDismissNetwork
                    )
                }
            }
            else -> Unit
        }
    }
}

@Preview
@Composable
fun ConnectivityObserverLayoutPreview() {
    ComiclyTheme {
        ConnectivityObserverLayoutContent(
            status = ConnectivityStatus.AVAILABLE,
            onDismissNetwork = { },
            content = { }
        )
    }
}