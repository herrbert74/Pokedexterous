package com.zsoltbertalan.pokedexterous.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.zsoltbertalan.pokedexterous.presentation.design.PokedexTheme
import com.zsoltbertalan.pokedexterous.presentation.navigation.NavHostContainer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokedexActivity : ComponentActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			PokedexTheme {
				val navController = rememberNavController()

				NavHostContainer(navController = navController)
			}
		}
	}

}
