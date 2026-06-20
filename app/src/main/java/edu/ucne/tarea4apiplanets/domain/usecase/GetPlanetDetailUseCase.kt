package edu.ucne.tarea4apiplanets.domain.usecase

import edu.ucne.tarea4apiplanets.data.remote.Resource
import edu.ucne.tarea4apiplanets.data.remote.dto.PlanetDto
import edu.ucne.tarea4apiplanets.domain.repository.PlanetRepository
import javax.inject.Inject

class GetPlanetDetailUseCase @Inject constructor(
    private val repository: PlanetRepository
) {
    suspend operator fun invoke(id: Int): Resource<PlanetDto>{
        return repository.getPlanetDetail(id)
    }
}