package edu.ucne.tarea4apiplanets.presentation.apiplanets.list

import edu.ucne.tarea4apiplanets.domain.apiplanets.model.Planet


data class PlanetListUiState(
    val isLoading: Boolean = false,
    val planets: List<Planet> = emptyList(),
    val error: String? = null,
    val filterName: String = "",
    val filterIsDestroyed: Boolean? = null
)
