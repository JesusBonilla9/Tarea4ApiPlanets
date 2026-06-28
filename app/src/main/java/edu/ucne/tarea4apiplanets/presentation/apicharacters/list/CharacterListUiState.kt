package edu.ucne.tarea4apiplanets.presentation.apicharacters.list

import edu.ucne.tarea4apiplanets.domain.apicharacters.model.Character

data class CharacterListUiState(
    val isLoading: Boolean = false,
    val characters: List<Character> = emptyList(),
    val error: String? = null,
    val filterName: String = "",
    val filterGender: String = "",
    val filterRace: String = ""
)
