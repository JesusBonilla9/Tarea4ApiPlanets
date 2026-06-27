package edu.ucne.tarea4apiplanets.data.apiplanets.remote.remotedatasource

import edu.ucne.tarea4apiplanets.data.apiplanets.remote.DragonBallApi
import edu.ucne.tarea4apiplanets.data.apiplanets.remote.dto.CharacterDto
import edu.ucne.tarea4apiplanets.data.apiplanets.remote.dto.CharacterResponseDto
import retrofit2.HttpException
import javax.inject.Inject

class CharacterRemoteDataSource @Inject constructor(
    private val api: DragonBallApi
) {
    suspend fun getCharacters(
        page: Int,
        limit: Int,
        name: String?,
        gender: String?,
        race: String?
    ): Result<CharacterResponseDto> {
        return try {
            val response = api.getCharacters(page, limit, name, gender, race)
            if (!response.isSuccessful) {
                return Result.failure(Exception("Error de red ${response.code()}"))
            }
            Result.success(response.body()!!)
        } catch (e: HttpException) {
            Result.failure(Exception("Error de servidor", e))
        } catch (e: Exception) {
            Result.failure(Exception("Error desconocido", e))
        }
    }

    suspend fun getCharacterDetail(id: Int): Result<CharacterDto> {
        return try {
            val response = api.getCharacterDetail(id)
            if (!response.isSuccessful) {
                return Result.failure(Exception("Error de red ${response.code()}"))
            }
            Result.success(response.body()!!)
        } catch (e: HttpException) {
            Result.failure(Exception("Error de servidor", e))
        } catch (e: Exception) {
            Result.failure(Exception("Error desconocido", e))
        }
    }
}