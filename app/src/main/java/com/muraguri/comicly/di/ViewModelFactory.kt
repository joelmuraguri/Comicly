package com.muraguri.comicly.di

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.muraguri.comicly.ComiclyApp
import com.muraguri.comicly.features.explore.character.CharacterViewModel
import com.muraguri.comicly.features.home.HomeViewModel
import com.muraguri.comicly.features.preference.PreferenceViewModel
import com.muraguri.comicly.ui.connectivity.ConnectivityObserverViewModel

object ViewModelFactory {

    val Factory = viewModelFactory {
        initializer {
            ConnectivityObserverViewModel(
                coreUseCases = comiclyApplication().container.coreUseCases
            )
        }
        initializer {
            PreferenceViewModel(
                coreUseCases = comiclyApplication().container.coreUseCases
            )
        }
        initializer {
            HomeViewModel(
                coreUseCases = comiclyApplication().container.coreUseCases
            )
        }
        initializer {
            CharacterViewModel(
                coreUseCases = comiclyApplication().container.coreUseCases,
                savedStateHandle = this.createSavedStateHandle()
            )
        }
    }


}

fun CreationExtras.comiclyApplication() : ComiclyApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ComiclyApp)