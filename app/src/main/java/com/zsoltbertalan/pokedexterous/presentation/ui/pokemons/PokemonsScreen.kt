package com.zsoltbertalan.pokedexterous.presentation.ui.pokemons

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.zsoltbertalan.pokedexterous.domain.model.PokemonItem
import com.zsoltbertalan.pokedexterous.presentation.component.paging.FirstPageErrorIndicator
import com.zsoltbertalan.pokedexterous.presentation.component.paging.FirstPageProgressIndicator
import com.zsoltbertalan.pokedexterous.presentation.component.paging.NewPageErrorIndicator
import com.zsoltbertalan.pokedexterous.presentation.component.paging.NewPageProgressIndicator
import com.zsoltbertalan.pokedexterous.presentation.component.paging.PaginatedLazyColumn
import com.zsoltbertalan.pokedexterous.presentation.component.paging.PaginationState
import com.zsoltbertalan.pokedexterous.presentation.design.PokedexTheme

@Composable
fun PokemonsScreen(
	pokemonPaginatedState: PaginationState<Int, PokemonItem>,

	modifier: Modifier = Modifier,
	onItemClick: (String, String) -> Unit,
) {

	Scaffold(
		topBar = {
			TopAppBar(
				colors = topAppBarColors(
					containerColor = PokedexTheme.colorScheme.primaryContainer,
					titleContentColor = PokedexTheme.colorScheme.onPrimaryContainer,
				),
				title = {
					Text("Pokedexterous")
				},
			)
		}
	) { innerPadding ->

			PaginatedLazyColumn(
				pokemonPaginatedState,
				firstPageProgressIndicator = { FirstPageProgressIndicator() },
				newPageProgressIndicator = { NewPageProgressIndicator() },
				firstPageErrorIndicator = { e ->
					FirstPageErrorIndicator(
						exception = e,
						onRetryClicked = {
							pokemonPaginatedState.retryLastFailedRequest()
						}
					)
				},
				newPageErrorIndicator = { e ->
					NewPageErrorIndicator(
						exception = e,
						onRetryClicked = {
							pokemonPaginatedState.retryLastFailedRequest()
						}
					)
				},
				modifier = modifier
					.padding(innerPadding)
					.fillMaxHeight(),
			) {
				itemsIndexed(
					pokemonPaginatedState.allItems,
				) { _, item ->
					PokemonRow(pokemon = item, onItemClicked = onItemClick)
				}
			}
	}
}
