package edu.ucne.tarea4apiplanets.presentation.list

import edu.ucne.tarea4apiplanets.data.remote.dto.PlanetDto

data class PlanetListUiState(
    val isLoading: Boolean = false,
    val planets: List<PlanetDto> = emptyList(),
    val error: String? = null,
    val filterName: String = "",
    val filterIsDestroyed: Boolean? = null
)
