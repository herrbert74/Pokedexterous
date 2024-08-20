package com.zsoltbertalan.pokedexterous.data.repository

import com.zsoltbertalan.pokedexterous.common.util.Outcome
import com.zsoltbertalan.pokedexterous.data.db.PokemonDataSource
import com.zsoltbertalan.pokedexterous.data.network.PAGING_PAGE_SIZE
import com.zsoltbertalan.pokedexterous.data.network.PokedexService
import com.zsoltbertalan.pokedexterous.data.network.dto.PokemonDetailsDto
import com.zsoltbertalan.pokedexterous.data.network.dto.PokemonListResponseDto
import com.zsoltbertalan.pokedexterous.data.network.dto.toPokemonDetails
import com.zsoltbertalan.pokedexterous.data.network.dto.toPokemonReply
import com.zsoltbertalan.pokedexterous.data.repository.getresult.fetchCacheThenNetwork
import com.zsoltbertalan.pokedexterous.domain.api.PokedexterousRepository
import com.zsoltbertalan.pokedexterous.domain.model.PageData
import com.zsoltbertalan.pokedexterous.domain.model.PagingReply
import com.zsoltbertalan.pokedexterous.domain.model.PokemonDetails
import com.zsoltbertalan.pokedexterous.domain.model.PokemonItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokedexterousAccessor @Inject constructor(
	private val pokedexService: PokedexService,
	private val pokemonDataSource: PokemonDataSource,
) : PokedexterousRepository {

	override fun getPokemonPage(page: Int): Flow<Outcome<PagingReply<PokemonItem>>> {
		return fetchCacheThenNetwork(
			fetchFromLocal = { pokemonDataSource.getPokemonPage(page) },
			makeNetworkRequest = { pokedexService.getPokemonPage((page) * PAGING_PAGE_SIZE) },
			saveResponseData = { listResponseDto ->
				pokemonDataSource.insertPageData(
					PageData(
						page,
						listResponseDto.next ?: "",
						listResponseDto.previous ?: "",
						listResponseDto.count ?: 0
					)
				)

				pokemonDataSource.insertPokemons(listResponseDto.toPokemonReply().pagingList, page)
			},
			mapper = PokemonListResponseDto::toPokemonReply,
		)
	}

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
