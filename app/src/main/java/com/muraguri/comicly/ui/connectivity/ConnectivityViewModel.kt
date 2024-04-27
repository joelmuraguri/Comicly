package com.muraguri.comicly.ui.connectivity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muraguri.comicly.core.domain.models.utils.ConnectivityStatus
import com.muraguri.comicly.core.domain.use_cases.CoreUseCases
import com.muraguri.comicly.ui.connectivity.utils.ConnectivityObserverEvent
import com.muraguri.comicly.ui.connectivity.utils.ConnectivityState
import kotlinx.coroutines.launch

class ConnectivityObserverViewModel (
    private val coreUseCases: CoreUseCases
) : ViewModel() {

    var state by mutableStateOf(ConnectivityState())
        private set

    init {
        getNetworkStatus()
    }

    fun onEvent(event: ConnectivityObserverEvent) {
        when (event) {
            ConnectivityObserverEvent.OnDismissNetworkError -> {
                state = state.copy(
                    status = ConnectivityStatus.IDLE
                )
            }
        }
    }

    private fun getNetworkStatus() {
        viewModelScope.launch {
            coreUseCases.connectivityObserverUseCases.invoke().collect { status ->
                when (status) {
                    ConnectivityStatus.AVAILABLE -> {
                        state = state.copy(
                            status = ConnectivityStatus.AVAILABLE
                        )
                    }
                    ConnectivityStatus.LOSING -> {
                        state = state.copy(
                            status = ConnectivityStatus.LOSING
                        )
                    }
                    ConnectivityStatus.LOST -> {
                        state = state.copy(
                            status = ConnectivityStatus.LOST
                        )
                    }
                    ConnectivityStatus.UNAVAILABLE -> {
                        state = state.copy(
                            status = ConnectivityStatus.UNAVAILABLE
                        )
                    }
                    else -> Unit
                }
            }
        }
    }

}