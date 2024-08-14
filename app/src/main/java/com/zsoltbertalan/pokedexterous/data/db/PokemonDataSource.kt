package com.zsoltbertalan.pokedexterous.data.db

import com.zsoltbertalan.pokedexterous.domain.model.Pokemon
import com.zsoltbertalan.pokedexterous.domain.model.PokemonDetails
import kotlinx.coroutines.flow.Flow

interface PokemonDataSource {

	suspend fun purgeDatabase()

	suspend fun insertPokemons(pokemons: List<Pokemon>)

	suspend fun insertPokemonDetails(pokemonDetails: PokemonDetails)

	fun getPokemons(): Flow<List<Pokemon>?>

	fun getPokemonDetails(id: String): Flow<PokemonDetails?>

}
