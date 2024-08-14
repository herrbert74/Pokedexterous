package com.zsoltbertalan.pokedexterous.data.network.dto

import com.zsoltbertalan.pokedexterous.domain.model.PokemonItem

data class PokemonListResponseDto(
	val count: Int? = null,
	val next: String? = null,
	val previous: Any? = null,
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

fun PokemonListResponseDto.toPokemonList() = this.results.map {
	it?.toPokemonItem() ?: PokemonItem("", "")
}
