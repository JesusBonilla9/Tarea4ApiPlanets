package edu.ucne.tarea4apiplanets.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlanetsResponseDto(
    val items: List<PlanetDto>
)
