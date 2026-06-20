package edu.ucne.tarea4apiplanets.presentation.detail

import edu.ucne.tarea4apiplanets.data.remote.dto.PlanetDto

data class DetailPlanetUiState (
    val isLoading: Boolean = false,
    val planet: PlanetDto? = null,
    val error: String? = null
)