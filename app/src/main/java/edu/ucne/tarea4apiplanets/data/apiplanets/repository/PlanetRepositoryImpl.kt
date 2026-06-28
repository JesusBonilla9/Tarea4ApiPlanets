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

        val result = remoteDataSource.getPlanets(page, limit, name, isDestroyed)
        result.onSuccess { planetsResponse ->
            val domainPlanets = planetsResponse.items.map { it.toDomain() }
            emit(Resource.Success(domainPlanets))
        }.onFailure { exception ->
            if (!name.isNullOrBlank() || isDestroyed != null) {
                val alternativeResult = remoteDataSource.getPlanets(
                    page = 1,
                    limit = 50,
                    name = null,
                    isDestroyed = null
                )
                alternativeResult.onSuccess { alternativeResponse ->
                    val filteredList = alternativeResponse.items
                        .filter { item ->
                            val matchedName = name.isNullOrBlank() || item.name.contains(name, ignoreCase = true)
                            val matchedDestroyed = isDestroyed == null || item.isDestroyed == isDestroyed

                            matchedName && matchedDestroyed
                        }
                        .map { it.toDomain() }
                    emit(Resource.Success(filteredList))
                }.onFailure {
                    emit(Resource.Error(exception.message ?: "Error desconocido"))
                }
            } else {
                emit(Resource.Error(exception.message ?: "Error desconocido"))
            }
        }
    }

    override fun getPlanetDetail(id: Int): Flow<Resource<Planet>> = flow {
        emit(Resource.Loading())
        val result = remoteDataSource.getPlanetDetail(id)
        result.onSuccess { planetDto ->
            emit(Resource.Success(planetDto.toDomain()))
        }.onFailure { exception ->
            emit(Resource.Error(exception.message ?: "Error desconocido"))
        }
    }
}
