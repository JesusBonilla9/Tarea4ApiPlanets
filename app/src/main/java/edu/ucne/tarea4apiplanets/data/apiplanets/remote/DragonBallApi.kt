package edu.ucne.tarea4apiplanets.data.apiplanets.remote

import edu.ucne.tarea4apiplanets.data.apiplanets.remote.dto.CharacterDto
import edu.ucne.tarea4apiplanets.data.apiplanets.remote.dto.CharacterResponseDto
import edu.ucne.tarea4apiplanets.data.apiplanets.remote.dto.PlanetDto
import edu.ucne.tarea4apiplanets.data.apiplanets.remote.dto.PlanetsResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DragonBallApi{
    @GET("planets")
    suspend fun getPlanets(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("name") name: String?,
        @Query("isDestroyed") isDestroyed: Boolean?
    ): Response<PlanetsResponseDto>

    @GET("planets/{id}")
    suspend fun getPlanetDetail(
        @Path("id") id: Int
    ): Response<PlanetDto>

    @GET("characters")
    suspend fun getCharacters(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("name") name: String?,
        @Query("gender") gender: String?,
        @Query("race") race: String?
    ): Response<CharacterResponseDto>

    @GET("characters/{id}")
    suspend fun getCharacterDetail(
        @Path("id") id: Int
    ): Response<CharacterDto>
}