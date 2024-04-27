package com.muraguri.comicly.core.domain.repo

import com.muraguri.comicly.core.domain.models.utils.ConnectivityStatus
import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    fun observe(): Flow<ConnectivityStatus>
}