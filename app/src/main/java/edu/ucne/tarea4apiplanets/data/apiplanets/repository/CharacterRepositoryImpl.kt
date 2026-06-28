package edu.ucne.tarea4apiplanets.data.apiplanets.repository

import edu.ucne.tarea4apiplanets.data.apiplanets.remote.Resource
import edu.ucne.tarea4apiplanets.data.apiplanets.remote.remotedatasource.CharacterRemoteDataSource
import edu.ucne.tarea4apiplanets.domain.apicharacters.model.Character
import edu.ucne.tarea4apiplanets.domain.apicharacters.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val remoteDataSource: CharacterRemoteDataSource
) : CharacterRepository {

    override fun getCharacters(
        page: Int,
        limit: Int,
        name: String?,
        gender: String?,
        race: String?
    ): Flow<Resource<List<Character>>> = flow {
        emit(Resource.Loading())

        val result = remoteDataSource.getCharacters(page, limit, name, gender, race)
        result.onSuccess { charactersResponse ->
            val domainCharacters = charactersResponse.items.map { it.toDomain() }
            emit(Resource.Success(domainCharacters))
        }.onFailure { exception ->
            if (!name.isNullOrBlank() || !gender.isNullOrBlank() || !race.isNullOrBlank()) {
                val alternativeResult = remoteDataSource.getCharacters(
                    page = 1,
                    limit = 100,
                    name = null,
                    gender = null,
                    race = null
                )
                alternativeResult.onSuccess { alternativeResponse ->
                    val filteredList = alternativeResponse.items
                        .filter { item ->
                            val matchedName = name.isNullOrBlank() || item.name.contains(name, ignoreCase = true)
                            val matchedGender = gender.isNullOrBlank() || item.gender.equals(gender, ignoreCase = true)
                            val matchedRace = race.isNullOrBlank() || item.race.contains(race, ignoreCase = true)

                            matchedName && matchedGender && matchedRace
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

    override fun getCharacterDetail(id: Int): Flow<Resource<Character>> = flow {
        emit(Resource.Loading())

        val result = remoteDataSource.getCharacterDetail(id)
        result.onSuccess { characterDto ->
            emit(Resource.Success(characterDto.toDomain()))
        }.onFailure { exception ->
            emit(Resource.Error(exception.message ?: "Error desconocido"))
        }
    }
}
