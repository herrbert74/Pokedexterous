package com.zsoltbertalan.pokedexterous.presentation.ui.pokemons

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.zsoltbertalan.pokedexterous.R
import com.zsoltbertalan.pokedexterous.domain.model.Failure
import com.zsoltbertalan.pokedexterous.domain.model.PokemonItem
import com.zsoltbertalan.pokedexterous.presentation.component.ShowLoading
import com.zsoltbertalan.pokedexterous.presentation.design.Dimens
import com.zsoltbertalan.pokedexterous.presentation.design.PokedexTheme
import com.zsoltbertalan.pokedexterous.presentation.design.PokedexTypography

@Composable
fun PokemonsScreen(
	pokemonItems: LazyPagingItems<PokemonItem>,
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
		LazyColumn(
			modifier = modifier
				.padding(innerPadding)
				.fillMaxHeight()
		) {
			showPokemons(pokemonItems, onItemClick)
		}
	}
}

private fun LazyListScope.showPokemons(
	pokemonItems: LazyPagingItems<PokemonItem>,
	navigateToDetail: (String, String) -> Unit,
) {
	items(pokemonItems.itemCount) { index ->
		pokemonItems[index].let {
			it?.let {
				Text(
					modifier = Modifier
						.testTag("PokemonItem")
						.padding(Dimens.marginLarge)
						.fillMaxWidth()
						.clickable { navigateToDetail(it.name, it.id) },
					text = it.name,
					style = PokedexTypography.titleLarge,
				)
			}

		}
	}

	when {
		pokemonItems.loadState.refresh is LoadState.Loading -> item {
			ShowLoading()
		}

		pokemonItems.loadState.append is LoadState.Loading -> item {
			ShowLoading()
		}

		pokemonItems.loadState.refresh is LoadState.Error -> item {
			val stateError = (pokemonItems.loadState.refresh as LoadState.Error)
			ErrorView(
				failure = Failure.ServerError(stateError.error.message ?: "Not loading"),
				onReload = { pokemonItems.refresh() }
			)
		}
	}
}

@Composable
private fun ErrorView(
	failure: Failure,
	onReload: () -> Unit
) {
	Column(
		Modifier
			.fillMaxSize(1f)
			.padding(Dimens.marginLarge),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		Image(
			painter = painterResource(R.drawable.ic_business_error),
			contentDescription = null
		)
		Text(
			text = (failure as Failure.ServerError).message,
			modifier = Modifier
				.fillMaxWidth()
				.padding(Dimens.marginLarge)
				.align(Alignment.CenterHorizontally),
			textAlign = TextAlign.Center,
			style = PokedexTypography.titleLarge,
		)
		Button(
			modifier = Modifier
				.padding(Dimens.marginLarge),
			onClick = { onReload() }
		) {
			Text(text = "Reload")
		}
	}
}
