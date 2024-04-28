package com.muraguri.comicly.features.preference

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muraguri.comicly.core.domain.use_cases.CoreUseCases
import com.muraguri.comicly.core.domain.utils.Resource
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

class PreferenceViewModel(
    private val coreUseCases: CoreUseCases
) : ViewModel() {

    private val _state = mutableStateOf(PrefScreenState())
    val state: State<PrefScreenState> = _state

    init {
        getCharacters()
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