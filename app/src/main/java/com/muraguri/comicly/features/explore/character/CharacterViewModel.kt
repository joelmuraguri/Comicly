package com.muraguri.comicly.features.explore.character

import androidx.compose.runtime.State

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muraguri.comicly.core.domain.models.comics.CharacterInfo
import com.muraguri.comicly.core.domain.use_cases.CoreUseCases
import com.muraguri.comicly.core.domain.utils.Resource
import com.muraguri.comicly.utils.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber

class CharacterViewModel(
    private val coreUseCases: CoreUseCases,
    savedStateHandle: SavedStateHandle
)  : ViewModel(){


    private val _state = mutableStateOf(CharacterInfoState())
    val state: State<CharacterInfoState> = _state

    private val _selectedCharacter = MutableStateFlow<Resource<CharacterInfo?>>(Resource.Loading)
    val selectedCharacter: StateFlow<Resource<CharacterInfo?>> = _selectedCharacter

    init {
        savedStateHandle.get<Int>(Constants.CHARACTER_ID.toString())?.let { coinId ->
            Timber.d("ID", coinId)
            getCharacterInfo(coinId)
        }
    }

    fun onEvents(events: CharacterEvents){
        when(events){
            is CharacterEvents.GetSelectedCharacter -> {
                viewModelScope.launch {
                    coreUseCases.fetchCharacterInfoUseCase(events.id).collect{ resource ->
                        _selectedCharacter.value = resource
                    }
                }
            }
            else -> {}
        }
    }

    private fun getCharacterInfo(characterId :Int){
        viewModelScope.launch {
            coreUseCases.fetchCharacterInfoUseCase(characterId).onEach { resource ->
                when(resource){
                    is Resource.Failure -> {
                        _state.value = CharacterInfoState( error = resource.error.localizedMessage ?: "An unexpected error occurred")
                    }
                    Resource.Loading -> {
                        _state.value = CharacterInfoState(isLoading = true)
                    }
                    is Resource.Success -> {
                        _state.value = CharacterInfoState(character = resource.data)
                    }
                }
            }
        }
    }


}