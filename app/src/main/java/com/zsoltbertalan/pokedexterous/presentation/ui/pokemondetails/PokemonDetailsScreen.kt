package com.zsoltbertalan.pokedexterous.presentation.ui.pokemondetails

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.zsoltbertalan.pokedexterous.R
import com.zsoltbertalan.pokedexterous.presentation.design.Colors
import com.zsoltbertalan.pokedexterous.presentation.design.Dimens
import com.zsoltbertalan.pokedexterous.presentation.design.PokedexTheme
import com.zsoltbertalan.pokedexterous.presentation.design.PokedexTypography
import com.zsoltbertalan.pokedexterous.presentation.design.titleLargeBold
import com.zsoltbertalan.pokedexterous.presentation.design.titleMediumBold
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow

@Composable
fun PokemonDetailsScreen(
	stateFlow: StateFlow<PokemonDetailsViewModel.UiState>,
	modifier: Modifier = Modifier,
	onBackClick: () -> Unit,
) {

	val uiState by stateFlow.collectAsStateWithLifecycle()

	BackHandler(onBack = { onBackClick() })

	val pokemon = uiState.pokemonDetails

	Scaffold(
		topBar = {
			TopAppBar(
				colors = TopAppBarDefaults.topAppBarColors(
					containerColor = PokedexTheme.colorScheme.primaryContainer,
					titleContentColor = PokedexTheme.colorScheme.onPrimaryContainer,
				),
				title = {
					Text(uiState.pokemonDetails.name)
				},
				navigationIcon = {
					IconButton(onClick = { onBackClick() }) {
						Icon(
							imageVector = Icons.AutoMirrored.Filled.ArrowBack,
							contentDescription = "Finish",
							tint = MaterialTheme.colorScheme.onPrimaryContainer
						)
					}
				}
			)
		}
	) { innerPadding ->
		Column(
			modifier = modifier
				.background(Colors.primaryContainer)
				.padding(innerPadding)
				.testTag("PokemonDetails")
		) {
			Row(
				verticalAlignment = Alignment.CenterVertically,
				modifier = Modifier
					.background(Colors.primaryContainer)
					.wrapContentHeight()
					.fillMaxWidth()
			) {

				Column(
					modifier = Modifier
						.weight(2f)
						.background(Colors.primaryContainer)
						.padding(vertical = Dimens.marginNormal, horizontal = Dimens.marginLarge)
						.testTag("PokemonsRow")
				) {

					Text(
						text = "ID: ${pokemon.id}",
						style = PokedexTypography.titleLargeBold,
						modifier = Modifier.padding(bottom = Dimens.marginLarge)
					)

					Text(
						text = "Height: ${pokemon.height}",
						style = PokedexTypography.titleMedium,
					)

				}

				val imageRequest = ImageRequest.Builder(LocalContext.current)
					.data(pokemon.imageUrl)
					.dispatcher(Dispatchers.IO)
					.memoryCacheKey(pokemon.imageUrl)
					.diskCacheKey(pokemon.imageUrl)
					.diskCachePolicy(CachePolicy.ENABLED)
					.memoryCachePolicy(CachePolicy.ENABLED)
					.build()
				AsyncImage(
					model = imageRequest,
					contentDescription = null,
					modifier = Modifier
						.weight(1f)
						.padding(Dimens.marginSmall)
						.clip(RoundedCornerShape(Dimens.marginLarge))
						.testTag("PokemonImage"),
					placeholder = painterResource(R.drawable.ic_transparent),
					error = painterResource(id = R.drawable.ic_error),
					contentScale = ContentScale.FillWidth,
				)
			}
		}
	}

}
