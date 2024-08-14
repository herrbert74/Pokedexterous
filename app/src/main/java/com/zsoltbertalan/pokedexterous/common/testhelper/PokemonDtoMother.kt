package com.zsoltbertalan.pokedexterous.common.testhelper

import com.zsoltbertalan.pokedexterous.data.network.dto.PokemonListResponseDto

object PokemonDtoMother {

	fun createPokemonDtoList() = PokemonListResponseDto(
		1300,
		"",
		"",
		listOf(
			createDefaultPokemonDto(name = "name1", url = "1"),
			createDefaultPokemonDto(name = "name2", url = "2"),
			createDefaultPokemonDto(name = "name3", url = "3"),
			createDefaultPokemonDto(name = "name4", url = "4"),
			createDefaultPokemonDto(name = "name5", url = "5"),
			createDefaultPokemonDto(name = "name6", url = "6"),
			createDefaultPokemonDto(name = "name7", url = "7"),
			createDefaultPokemonDto(name = "name8", url = "8"),
		)
	)

}

private fun createDefaultPokemonDto(
	name: String = "",
	url: String = ""
): PokemonListResponseDto.Result = PokemonListResponseDto.Result(name, url)
