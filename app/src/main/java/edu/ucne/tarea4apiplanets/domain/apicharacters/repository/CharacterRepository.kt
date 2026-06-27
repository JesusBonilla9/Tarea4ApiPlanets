package edu.ucne.tarea4apiplanets.domain.apicharacters.repository

import edu.ucne.tarea4apiplanets.data.apiplanets.remote.Resource
import kotlinx.coroutines.flow.Flow
import edu.ucne.tarea4apiplanets.domain.apicharacters.model.Character

interface CharacterRepository {
    fun getCharacters(
        page: Int,
        limit: Int,
        name: String?,
        gender: String?,
        race: String?
    ): Flow<Resource<List<Character>>>

    fun getCharacterDetail(id: Int): Flow<Resource<Character>>
}