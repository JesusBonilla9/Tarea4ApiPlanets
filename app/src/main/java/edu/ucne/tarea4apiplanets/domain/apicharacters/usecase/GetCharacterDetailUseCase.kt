package edu.ucne.tarea4apiplanets.domain.apicharacters.usecase

import edu.ucne.tarea4apiplanets.domain.apicharacters.repository.CharacterRepository
import javax.inject.Inject

class GetCharacterDetailUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    operator fun invoke(id: Int) = repository.getCharacterDetail(id)
}