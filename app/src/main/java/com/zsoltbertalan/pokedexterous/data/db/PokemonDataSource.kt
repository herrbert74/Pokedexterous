package com.zsoltbertalan.pokedexterous.data.db

import com.zsoltbertalan.pokedexterous.domain.model.PageData
import com.zsoltbertalan.pokedexterous.domain.model.PagingReply
import com.zsoltbertalan.pokedexterous.domain.model.PokemonDetails
import com.zsoltbertalan.pokedexterous.domain.model.PokemonItem
import kotlinx.coroutines.flow.Flow

interface PokemonDataSource {

	suspend fun purgeDatabase()

	suspend fun insertPageData(page: PageData)

	suspend fun getPageData(page: Int): PageData?

	suspend fun getAllPageData(): List<PageData>

	suspend fun insertPokemons(pokemons: List<PokemonItem>, page: Int)

	fun getPokemonPage(page: Int): Flow<PagingReply<PokemonItem>?>

	suspend fun insertPokemonDetails(pokemonDetails: PokemonDetails)

	fun getPokemonDetails(id: String): Flow<PokemonDetails?>

}
