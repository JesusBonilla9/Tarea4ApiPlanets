package edu.ucne.tarea4apiplanets.presentation.apiplanets.list

sealed interface PlanetListUiEvent {
    data class UpdateFilters(
        val name: String,
        val isDestroyed: Boolean?
    ) : PlanetListUiEvent

    data object Search : PlanetListUiEvent
}