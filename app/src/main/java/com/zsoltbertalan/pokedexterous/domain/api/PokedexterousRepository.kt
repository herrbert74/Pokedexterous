package com.zsoltbertalan.pokedexterous.domain.api

import androidx.paging.PagingData
import com.zsoltbertalan.pokedexterous.common.util.Outcome
import com.zsoltbertalan.pokedexterous.domain.model.PokemonDetails
import com.zsoltbertalan.pokedexterous.domain.model.PokemonItem
import kotlinx.coroutines.flow.Flow

interface PokedexterousRepository {
	fun getAllPokemons(): Flow<PagingData<PokemonItem>>
	fun getPokemonDetails(pokemonId: String): Flow<Outcome<PokemonDetails>>
}
