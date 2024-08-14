package com.zsoltbertalan.pokedexterous.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import com.zsoltbertalan.pokedexterous.presentation.ui.pokemondetails.PokemonDetailsScreen
import com.zsoltbertalan.pokedexterous.presentation.ui.pokemondetails.PokemonDetailsViewModel
import com.zsoltbertalan.pokedexterous.presentation.ui.pokemons.PokemonsScreen
import com.zsoltbertalan.pokedexterous.presentation.ui.pokemons.PokemonsViewModel

@SuppressLint("RestrictedApi")
@Composable
fun NavHostContainer(
	navController: NavHostController,
) {
	val pokemonsViewModel = hiltViewModel<PokemonsViewModel>()

	NavHost(
		navController = navController,
		startDestination = Destination.POKEMONS.route,
		modifier = Modifier,
		builder = {
			composable(Destination.POKEMONS.route) {
				PokemonsScreen(
					upcoming = pokemonsViewModel.pokemonList.collectAsLazyPagingItems(),
					onItemClick = { name, id->
						if (navController.currentDestination ==
							navController.findDestination(Destination.POKEMONS.route)
						) {
							navController.navigate("details/$name/$id")
						}
					},
					onReload = {

					}
				)
			}
			composable(
				Destination.DETAILS.route,
				arguments = listOf(
					navArgument("pokemonName") { type = NavType.StringType },
					navArgument("pokemonId") { type = NavType.StringType },
				),
				enterTransition = {
					slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start)
				},
				popExitTransition = {
					slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End)
				}
			) {
				val detailsViewModel = hiltViewModel<PokemonDetailsViewModel>()
				PokemonDetailsScreen(
					stateFlow = detailsViewModel.state,
				) {
					navController.popBackStack()
				}
			}
		}
	)
}