package edu.ucne.tarea4apiplanets.data.apiplanets.remote.remotedatasource

import edu.ucne.tarea4apiplanets.data.apiplanets.remote.DragonBallApi
import edu.ucne.tarea4apiplanets.data.apiplanets.remote.dto.PlanetDto
import edu.ucne.tarea4apiplanets.data.apiplanets.remote.dto.PlanetsResponseDto
import retrofit2.HttpException
import javax.inject.Inject

class PlanetRemoteDataSource @Inject constructor(
    private val api: DragonBallApi
) {

    suspend fun getPlanets(
        page: Int,
        limit: Int,
        name: String?,
        isDestroyed: Boolean?
    ): Result<PlanetsResponseDto> {
        try {
            val response = api.getPlanets(page, limit, name, isDestroyed)
            if (!response.isSuccessful) {
                return Result.failure(Exception("Error de red ${response.code()}"))
            }
            return Result.success(response.body()!!)
        } catch (e: HttpException) {
            return Result.failure(Exception("Error de servidor", e))
        } catch (e: Exception) {
            return Result.failure(Exception("Error desconocido", e))
        }
    }

    suspend fun getPlanetDetail(id: Int): Result<PlanetDto> {
        try {
            val response = api.getPlanetDetail(id)
            if (!response.isSuccessful) {
                return Result.failure(Exception("Error de red ${response.code()}"))
            }
            return Result.success(response.body()!!)
        } catch (e: HttpException) {
            return Result.failure(Exception("Error de servidor", e))
        } catch (e: Exception) {
            return Result.failure(Exception("Error desconocido", e))
        }
    }
}
