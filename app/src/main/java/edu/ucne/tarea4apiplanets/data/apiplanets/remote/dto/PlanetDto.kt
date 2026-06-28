package edu.ucne.tarea4apiplanets.data.apiplanets.remote.dto

import edu.ucne.tarea4apiplanets.domain.apiplanets.model.Planet

data class PlanetDto(
    val id: Int,
    val name: String,
    val isDestroyed: Boolean,
    val description: String,
    val image: String
){
    fun toDomain() = Planet(
        id, name, isDestroyed, description, image
    )
}
