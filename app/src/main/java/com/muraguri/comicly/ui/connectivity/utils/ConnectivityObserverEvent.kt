package com.muraguri.comicly.ui.connectivity.utils

sealed class ConnectivityObserverEvent {
    data object OnDismissNetworkError : ConnectivityObserverEvent()
}
