package com.muraguri.comicly.features.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muraguri.comicly.core.domain.use_cases.CoreUseCases
import com.muraguri.comicly.core.domain.utils.Resource
import com.muraguri.comicly.core.local.entity.FavCharacter
import com.muraguri.comicly.navigation.Screens
import com.muraguri.comicly.utils.ComiclyEvents
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val coreUseCases: CoreUseCases
) : ViewModel() {

    private val _state = mutableStateOf(HomeScreenState())
    val state: State<HomeScreenState> = _state

    private val _allFavCharactersList = MutableStateFlow<List<FavCharacter>>(emptyList())
    val favCharactersList : StateFlow<List<FavCharacter>> = _allFavCharactersList

    private val _uiEvents = Channel<ComiclyEvents>()
    val uiEvents = _uiEvents.receiveAsFlow()

    init {
        fetchFavouriteCharacters()
        fetchNewComics()
    }

    fun onEvents(events: HomeEvents){
        when(events){
            is HomeEvents.OnCharacterClick -> {
                viewModelScope.launch {
                    _uiEvents.send(ComiclyEvents.Navigate(Screens.CharacterInfo.route + "?characterId=${events.character.id}"))
                }
            }
            is HomeEvents.OnIssueClick -> TODO()
        }
    }

    private fun fetchFavouriteCharacters(){
        viewModelScope.launch {
            coreUseCases.fetchFavCharactersUseCase().collect{ favCharacters->
                _allFavCharactersList.value = favCharacters
            }
        }
    }

    fun fetchNewComics(){
        viewModelScope.launch {
            coreUseCases.getIssuesUseCase.invoke().collect{ resource ->
                when(resource){
                    is Resource.Failure -> {
                        _state.value = _state.value.copy(
                            error = resource.error.message ?: "An error occurred",
                            isLoading = false
                        )
                    }
                    Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true, error = "")
                    }
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            allNewComics = resource.data,
                            isLoading = false,
                            error = ""
                        )
                    }
                }
            }
        }
    }

}