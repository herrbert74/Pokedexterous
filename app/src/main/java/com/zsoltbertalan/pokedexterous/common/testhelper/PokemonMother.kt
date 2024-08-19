package com.zsoltbertalan.pokedexterous.common.testhelper

import com.zsoltbertalan.pokedexterous.domain.model.PokemonItem

object PokemonMother {

	fun createPokemonList() = listOf(
		createDefaultPokemon(id = "1", name = "name1"),
		createDefaultPokemon(id = "2", name = "name2"),
		createDefaultPokemon(id = "3", name = "name3"),
		createDefaultPokemon(id = "4", name = "name4"),
		createDefaultPokemon(id = "5", name = "name5"),
		createDefaultPokemon(id = "6", name = "name6"),
		createDefaultPokemon(id = "7", name = "name7"),
		createDefaultPokemon(id = "8", name = "name8"),
	)

}

private fun createDefaultPokemon(
	id: String = "",
	name: String = ""
): PokemonItem = PokemonItem(name, id)
