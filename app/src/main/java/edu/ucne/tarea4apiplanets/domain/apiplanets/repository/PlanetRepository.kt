package edu.ucne.tarea4apiplanets.domain.apiplanets.repository

import edu.ucne.tarea4apiplanets.data.apiplanets.remote.Resource
import edu.ucne.tarea4apiplanets.domain.apiplanets.model.Planet
import kotlinx.coroutines.flow.Flow

interface PlanetRepository {
    fun getPlanets(
        page: Int,
        limit: Int,
        name: String?,
        isDestroyed: Boolean?
    ): Flow<Resource<List<Planet>>>

    fun getPlanetDetail(id: Int): Flow<Resource<Planet>>
}