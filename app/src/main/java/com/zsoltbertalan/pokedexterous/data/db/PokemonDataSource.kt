package com.zsoltbertalan.pokedexterous.data.db

import com.zsoltbertalan.pokedexterous.domain.model.PokemonDetails
import com.zsoltbertalan.pokedexterous.domain.model.PokemonItem
import kotlinx.coroutines.flow.Flow

interface PokemonDataSource {

	suspend fun purgeDatabase()

	suspend fun insertPokemons(pokemons: List<PokemonItem>)

	suspend fun insertPokemonDetails(pokemonDetails: PokemonDetails)

	fun getPokemons(): Flow<List<PokemonItem>?>

	fun getPokemonDetails(id: String): Flow<PokemonDetails?>

}
