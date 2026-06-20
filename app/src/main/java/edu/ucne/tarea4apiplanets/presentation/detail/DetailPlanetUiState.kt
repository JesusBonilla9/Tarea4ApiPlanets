package edu.ucne.tarea4apiplanets.presentation.detail

import edu.ucne.tarea4apiplanets.domain.model.Planet

data class DetailPlanetUiState (
    val isLoading: Boolean = false,
    val planet: Planet? = null,
    val error: String? = null
)