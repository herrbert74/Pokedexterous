package com.zsoltbertalan.pokedexterous.presentation.navigation

import androidx.annotation.StringRes
import com.zsoltbertalan.pokedexterous.R

enum class Destination(
	@StringRes val titleId: Int,
	val route: String
) {
	POKEMONS(R.string.pokemons, "pokemons"),
	DETAILS(R.string.details, "details/{pokemonName}/{pokemonId}"),
}
