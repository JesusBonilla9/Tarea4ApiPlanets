package edu.ucne.tarea4apiplanets.presentation.apicharacters.detail
import edu.ucne.tarea4apiplanets.domain.apicharacters.model.Character

data class DetailCharacterUiState(
    val isLoading: Boolean = false,
    val character: Character? = null,
    val error: String? = null
)
