package edu.ucne.tarea4apiplanets.domain.apicharacters.model

data class Character(
    val id: Int,
    val name: String,
    val ki: String,
    val maxKi: String,
    val race: String,
    val gender: String,
    val description: String,
    val image: String,
)
