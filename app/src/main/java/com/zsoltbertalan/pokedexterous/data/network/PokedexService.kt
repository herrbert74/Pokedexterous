package com.zsoltbertalan.pokedexterous.data.network

import com.zsoltbertalan.pokedexterous.data.network.dto.PokemonDetailsDto
import com.zsoltbertalan.pokedexterous.data.network.dto.PokemonListResponseDto
import com.zsoltbertalan.pokedexterous.data.repository.PAGING_PAGE_SIZE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokedexService {

	@GET("pokemon")
	suspend fun getPokemons(
		@Query("offset") offset: Int,
		@Query("limit") limit: Int = PAGING_PAGE_SIZE,
	): PokemonListResponseDto

	@GET("pokemon/{id}")
	suspend fun getPokemonDetails(
		@Path("id") id: String,
	): PokemonDetailsDto
}
