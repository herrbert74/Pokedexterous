package com.zsoltbertalan.pokedexterous.presentation.ui.pokemons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.zsoltbertalan.pokedexterous.common.testhelper.PokemonMother
import com.zsoltbertalan.pokedexterous.domain.model.PokemonItem
import com.zsoltbertalan.pokedexterous.presentation.design.Dimens
import com.zsoltbertalan.pokedexterous.presentation.design.PokedexTypography

@Composable
fun PokemonRow(
	pokemon: PokemonItem,
	onItemClicked: (String, String) -> Unit,
	modifier: Modifier = Modifier
) {

	Row(
		verticalAlignment = Alignment.CenterVertically,
		modifier = modifier
			.clickable { onItemClicked(pokemon.name, pokemon.id) }
			.padding(Dimens.marginLarge)
			.fillMaxWidth()
			.testTag("PokemonItem")
	) {

		Text(
			modifier = Modifier.padding(end = Dimens.marginLarge),
			text = pokemon.id,
			style = PokedexTypography.titleLarge,
		)

		Text(
			text = pokemon.name,
			style = PokedexTypography.titleLarge,
		)

	}

}

@Preview
@Composable
fun PokemonPreview() {
	PokemonRow(pokemon = PokemonMother.createPokemonList().first(), { _: String, _: String -> }, Modifier)
}
