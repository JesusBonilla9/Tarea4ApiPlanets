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

        val response = remoteDataSource.getCharacters(page, limit, name, gender, race)
        response.onSuccess { characters ->
            emit(Resource.Success(characters.items.map { it.toDomain() }))
        }.onFailure {
            emit(Resource.Error(it.message ?: "Error desconocido"))
        }
    }

    override fun getCharacterDetail(id: Int): Flow<Resource<Character>> = flow {
        emit(Resource.Loading())

        val response = remoteDataSource.getCharacterDetail(id)
        response.onSuccess { character ->
            emit(Resource.Success(character.toDomain()))
        }.onFailure {
            emit(Resource.Error(it.message ?: "Error desconocido"))
        }
    }
}