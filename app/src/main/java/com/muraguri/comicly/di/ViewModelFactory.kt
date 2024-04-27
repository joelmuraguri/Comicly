package com.muraguri.comicly.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.muraguri.comicly.ComiclyApp
import com.muraguri.comicly.ui.connectivity.ConnectivityObserverViewModel

object ViewModelFactory {

    val Factory = viewModelFactory {
        initializer {
            ConnectivityObserverViewModel(
                coreUseCases = comiclyApplication().container.coreUseCases
            )
        }
    }


}

fun CreationExtras.comiclyApplication() : ComiclyApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ComiclyApp)