package edu.ucne.tarea4apiplanets.domain.apiplanets.usecase

import edu.ucne.tarea4apiplanets.domain.apiplanets.repository.PlanetRepository
import javax.inject.Inject

class GetPlanetDetailUseCase @Inject constructor(
    private val repository: PlanetRepository
) {
    operator fun invoke(id: Int) = repository.getPlanetDetail(id)
}
