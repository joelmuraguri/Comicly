package com.muraguri.comicly.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muraguri.comicly.core.domain.use_cases.CoreUseCases
import com.muraguri.comicly.core.local.entity.FavCharacter
import com.muraguri.comicly.utils.ComiclyEvents
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val coreUseCases: CoreUseCases
) : ViewModel() {


    private val _allFavCharactersList = MutableStateFlow<List<FavCharacter>>(emptyList())
    val favCharactersList : StateFlow<List<FavCharacter>> = _allFavCharactersList

    private val _uiEvents = Channel<ComiclyEvents>()
    val uiEvents = _uiEvents.receiveAsFlow()

    init {
        fetchFavouriteCharacters()
    }

    private fun fetchFavouriteCharacters(){
        viewModelScope.launch {
            coreUseCases.fetchFavCharactersUseCase().collect{ favCharacters->
                _allFavCharactersList.value = favCharacters
            }
        }
    }
    fun fetchPopularVolumes(){}
    fun fetchPopularCharacters(){}
    fun fetchPopularIssues(){}

}