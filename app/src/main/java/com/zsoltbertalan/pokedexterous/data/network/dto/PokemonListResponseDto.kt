package com.zsoltbertalan.pokedexterous.data.network.dto

import com.zsoltbertalan.pokedexterous.domain.model.PagingReply
import com.zsoltbertalan.pokedexterous.domain.model.PokemonItem

data class PokemonListResponseDto(
	val count: Int? = null,
	val next: String? = null,
	val previous: String? = null,
	val results: List<Result?> = listOf()
) {
	data class Result(
		val name: String? = null,
		val url: String? = null
	)
}

fun PokemonListResponseDto.Result.toPokemonItem(): PokemonItem =
	PokemonItem(
		this.name ?: "",
		this.url
			?.replace("https://pokeapi.co/api/v2/pokemon/", "") //Extract the id
			?.replace("/", "")
			?: ""
	)

fun PokemonListResponseDto.toPokemonReply(): PagingReply<PokemonItem> {
	return PagingReply(
		this.results.map {
			it?.toPokemonItem() ?: PokemonItem("", "")
		},
		this.next.isNullOrEmpty(),
	)
}
