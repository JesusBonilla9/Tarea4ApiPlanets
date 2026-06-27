package edu.ucne.tarea4apiplanets.domain.apiplanets.usecase

import edu.ucne.tarea4apiplanets.domain.apiplanets.repository.PlanetRepository
import javax.inject.Inject

class GetPlanetsUseCase @Inject constructor(
    private val repository: PlanetRepository,
) {
    operator fun invoke(
        page: Int = 1,
        limit: Int = 10,
        name: String? = null,
        isDestroyed: Boolean? = null,
    ) = repository.getPlanets(page, limit, name, isDestroyed)
}
