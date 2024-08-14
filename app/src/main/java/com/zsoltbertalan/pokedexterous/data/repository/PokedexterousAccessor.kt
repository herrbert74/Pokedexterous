package com.zsoltbertalan.pokedexterous.data.repository

import com.github.michaelbull.result.map
import com.zsoltbertalan.pokedexterous.common.util.Outcome
import com.zsoltbertalan.pokedexterous.common.util.runCatchingApi
import com.zsoltbertalan.pokedexterous.data.db.PokemonDataSource
import com.zsoltbertalan.pokedexterous.data.network.PokedexService
import com.zsoltbertalan.pokedexterous.data.network.dto.PokemonDetailsDto
import com.zsoltbertalan.pokedexterous.data.network.dto.toPokemonDetails
import com.zsoltbertalan.pokedexterous.data.network.dto.toPokemonList
import com.zsoltbertalan.pokedexterous.data.repository.getresult.fetchCacheThenNetwork
import com.zsoltbertalan.pokedexterous.domain.api.PokedexterousRepository
import com.zsoltbertalan.pokedexterous.domain.model.PokemonDetails
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
class PokedexterousAccessor(
	private val pokedexService: PokedexService,
	private val pokemonDataSource: PokemonDataSource,
) : PokedexterousRepository {

	override fun getAllPokemons() = createPager { page ->
		pokedexService.runCatchingApi {
			getPokemons(offset = (page - 1) * PAGING_PAGE_SIZE)
		}.map { Pair(it.toPokemonList(), it.count ?: 0) }
	}.flow

	override fun getPokemonDetails(pokemonId: String): Flow<Outcome<PokemonDetails>> {
		return fetchCacheThenNetwork(
			fetchFromLocal = { pokemonDataSource.getPokemonDetails(pokemonId) },
			makeNetworkRequest = { pokedexService.getPokemonDetails(pokemonId) },
			saveResponseData = { pokemonDetails ->
				pokemonDataSource.insertPokemonDetails(pokemonDetails.toPokemonDetails())
			},
			mapper = PokemonDetailsDto::toPokemonDetails,
		)
	}

}
