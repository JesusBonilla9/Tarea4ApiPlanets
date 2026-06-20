package edu.ucne.tarea4apiplanets.domain.usecase

import edu.ucne.tarea4apiplanets.data.remote.Resource
import edu.ucne.tarea4apiplanets.data.remote.dto.PlanetDto
import edu.ucne.tarea4apiplanets.domain.repository.PlanetRepository
import javax.inject.Inject

class GetPlanetsUseCase @Inject constructor(
    private val repository: PlanetRepository
) {
    suspend operator fun invoke(
        page: Int=1,
        limit: Int=10,
        name: String? =null,
        isDestroyed: Boolean? = null,
    ): Resource<List<PlanetDto>> {
        return repository.getPlanets(page, limit, name, isDestroyed)
    }
}