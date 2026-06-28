package edu.ucne.tarea4apiplanets.presentation.apicharacters.list

sealed interface CharacterListUiEvent {
    data class UpdateFilters(
        val name: String,
        val gender: String,
        val race: String
    ) : CharacterListUiEvent

    data object Search : CharacterListUiEvent
}