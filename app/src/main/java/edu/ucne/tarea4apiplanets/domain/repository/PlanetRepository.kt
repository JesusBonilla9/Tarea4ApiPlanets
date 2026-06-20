package edu.ucne.tarea4apiplanets.domain.repository

import edu.ucne.tarea4apiplanets.data.remote.Resource
import edu.ucne.tarea4apiplanets.data.remote.dto.PlanetDto

interface PlanetRepository {
    suspend fun getPlanets(
        page: Int,
        limit: Int,
        name: String?,
        isDestroyed: Boolean?
    ): Resource<List<PlanetDto>>

    suspend fun getPlanetDetail(id: Int): Resource<PlanetDto>
}