package com.zsoltbertalan.pokedexterous.domain.api

import com.zsoltbertalan.pokedexterous.common.util.Outcome
import com.zsoltbertalan.pokedexterous.domain.model.PagingReply
import com.zsoltbertalan.pokedexterous.domain.model.PokemonDetails
import com.zsoltbertalan.pokedexterous.domain.model.PokemonItem
import kotlinx.coroutines.flow.Flow

interface PokedexterousRepository {
	fun getPokemonPage(page: Int): Flow<Outcome<PagingReply<PokemonItem>>>
	fun getPokemonDetails(pokemonId: String): Flow<Outcome<PokemonDetails>>
}
