package com.muraguri.comicly.ui.connectivity.utils

import com.muraguri.comicly.core.domain.models.utils.ConnectivityStatus

data class ConnectivityState(
    val status: ConnectivityStatus = ConnectivityStatus.IDLE
)
