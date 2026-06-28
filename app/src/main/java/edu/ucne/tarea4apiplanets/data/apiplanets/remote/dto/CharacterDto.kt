package edu.ucne.tarea4apiplanets.data.apiplanets.remote.dto

import edu.ucne.tarea4apiplanets.domain.apicharacters.model.Character

data class CharacterDto(
    val id: Int,
    val name: String,
    val ki: String,
    val race: String,
    val gender: String,
    val description: String,
    val image: String,
    val maxKi: String
) {
    fun toDomain() = Character(
        id, name, ki, maxKi, race, gender, description, image
    )
}
