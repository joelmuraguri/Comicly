package com.muraguri.comicly.features.preference

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muraguri.comicly.core.data.mapping.toFavouriteCharacterEntity
import com.muraguri.comicly.core.domain.use_cases.CoreUseCases
import com.muraguri.comicly.core.domain.utils.Resource
import kotlinx.coroutines.launch

class PreferenceViewModel(
    private val coreUseCases: CoreUseCases
) : ViewModel() {

    private val _state = mutableStateOf(PrefScreenState())
    val state: State<PrefScreenState> = _state

    init {
        getCharacters()
    }

    fun onEvents(event: PreferenceEvent){
        when(event){
            is PreferenceEvent.OnActiveChange -> {
                _state.value = _state.value.copy(
                    active = event.active
                )
            }
            is PreferenceEvent.OnQueryChange -> {
                _state.value = _state.value.copy(
                    query = event.query
                )
            }
            is PreferenceEvent.OnSearch -> {
                viewModelScope.launch {
                    coreUseCases.searchUseCase.invoke(event.query).collect{ resource ->
                        when(resource){
                            is Resource.Failure -> {
                                _state.value = _state.value.copy(
                                    searchError = resource.error.message ?: "An error occurred",
                                    searchLoadingState = false
                                )
                            }
                            Resource.Loading -> {
                                _state.value = _state.value.copy(searchLoadingState = true, searchError = "")
                            }
                            is Resource.Success -> {
                                _state.value = _state.value.copy(
                                    searchResults = resource.data
                                )
                            }
                        }
                    }
                }
            }
            is PreferenceEvent.OnUpdatedCharacter -> {
                val currentSelected = _state.value.selectedCharacters.toMutableList() // Create mutable copy
                val character = event.character

                if (currentSelected.contains(character)) {
                    currentSelected.remove(character)
                } else {
                    currentSelected.add(character)
                }
                _state.value = _state.value.copy(
                    selectedCharacters = currentSelected
                )
            }
            PreferenceEvent.OnUpdateFavCharacters -> {
                viewModelScope.launch {
                    val favCharacters = _state.value.selectedCharacters.map { it.toFavouriteCharacterEntity() }
                    coreUseCases.updateFavCharactersUseCase(favCharacters)
                }
            }
        }
    }

    fun getCharacters(){
        viewModelScope.launch {
            coreUseCases.getCharactersUseCase.invoke().collect{ resource ->
                when(resource){
                    is Resource.Failure -> {
                        _state.value = _state.value.copy(
                            error = resource.error.message ?: "An error occurred",
                            loadingState = false
                        )
                    }
                    Resource.Loading -> {
                        _state.value = _state.value.copy(loadingState = true, error = "")
                    }
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            characters = resource.data,
                            loadingState = false,
                            error = ""
                        )
                    }
                }
            }
        }
    }

}