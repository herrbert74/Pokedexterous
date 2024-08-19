package com.zsoltbertalan.pokedexterous.data.network

import com.zsoltbertalan.pokedexterous.data.network.dto.PokemonDetailsDto
import com.zsoltbertalan.pokedexterous.data.network.dto.PokemonListResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val PAGING_PAGE_SIZE: Int = 30

interface PokedexService {

	@GET("pokemon")
	suspend fun getPokemonPage(
		@Query("offset") offset: Int,
		@Query("limit") limit: Int = PAGING_PAGE_SIZE,
	): PokemonListResponseDto

	@GET("pokemon/{id}")
	suspend fun getPokemonDetails(
		@Path("id") id: String,
	): PokemonDetailsDto
}
