package edu.ucne.tarea4apiplanets.data.apiplanets.repository

import edu.ucne.tarea4apiplanets.data.apiplanets.remote.Resource
import edu.ucne.tarea4apiplanets.data.apiplanets.remote.remotedatasource.PlanetRemoteDataSource
import edu.ucne.tarea4apiplanets.domain.apiplanets.model.Planet
import edu.ucne.tarea4apiplanets.domain.apiplanets.repository.PlanetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlanetRepositoryImpl @Inject constructor(
    private val remoteDataSource: PlanetRemoteDataSource
) : PlanetRepository {

    override fun getPlanets(
        page: Int,
        limit: Int,
        name: String?,
        isDestroyed: Boolean?
    ): Flow<Resource<List<Planet>>> = flow {

        emit(Resource.Loading())

        val response = remoteDataSource.getPlanets(page, limit, name, isDestroyed)
        response.onSuccess { planets ->
            emit(Resource.Success(planets.items.map { it.toDomain() }))
        }.onFailure {
            emit(Resource.Error(it.message ?: "Error desconocido"))
        }
    }

    override fun getPlanetDetail(id: Int): Flow<Resource<Planet>> = flow {
        emit(Resource.Loading())
        val response = remoteDataSource.getPlanetDetail(id)
        response.onSuccess { planets ->
            emit(Resource.Success(planets.toDomain() ))
        }.onFailure {
            emit(Resource.Error(it.message ?: "Error desconocido"))
        }
    }
}