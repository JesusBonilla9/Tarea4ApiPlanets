package edu.ucne.tarea4apiplanets.presentation.apiplanets.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.tarea4apiplanets.data.apiplanets.remote.Resource
import edu.ucne.tarea4apiplanets.domain.apiplanets.usecase.GetPlanetsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanetListViewModel @Inject constructor(
    private val getPlanetsUseCase: GetPlanetsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PlanetListUiState())
    val state = _state.asStateFlow()

    init {
        loadPlanets()
    }

    fun onEvent(event: PlanetListUiEvent) {
        when (event) {
            is PlanetListUiEvent.UpdateFilters -> _state.update {
                it.copy(
                    filterName = event.name,
                    filterIsDestroyed = event.isDestroyed
                )
            }

            PlanetListUiEvent.Search -> loadPlanets()
        }
    }

    private fun loadPlanets() {
        viewModelScope.launch {
            val current = _state.value

            getPlanetsUseCase(
                name = current.filterName.takeIf { it.isNotBlank() },
                isDestroyed = current.filterIsDestroyed
            ).collect { result ->

                when (result) {
                    is Resource.Loading -> _state.update { it.copy(isLoading = true) }

                    is Resource.Success ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                planets = result.data ?: emptyList()
                            )
                        }

                    is Resource.Error ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = result.message
                            )
                        }
                }
            }
        }
    }
}