package com.zsoltbertalan.pokedexterous

import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zsoltbertalan.pokedexterous.common.async.IoDispatcher
import com.zsoltbertalan.pokedexterous.common.async.MainDispatcher
import com.zsoltbertalan.pokedexterous.domain.api.PokedexterousRepository
import com.zsoltbertalan.pokedexterous.presentation.ui.PokedexActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.CoroutineDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class PokemonDetailsTest {

	@get:Rule(order = 0)
	val hiltAndroidRule = HiltAndroidRule(this)

	@get:Rule(order = 1)
	val composeTestRule = createAndroidComposeRule<PokedexActivity>()

	@Inject
	lateinit var pokedexterousRepository: PokedexterousRepository

	@Inject
	@MainDispatcher
	lateinit var mainContext: CoroutineDispatcher

	@Inject
	@IoDispatcher
	lateinit var ioContext: CoroutineDispatcher

	@Before
	fun setUp() {

		hiltAndroidRule.inject()

	}

	@Test
	fun showPokemons() {

		composeTestRule.waitUntilAtLeastOneExists(hasTestTag("PokemonItem"), 1000L)

		composeTestRule.onNodeWithText("Bulbasaur", ignoreCase = true).assertExists()

	}

	@Test
	fun showPokemonDetails() {

		composeTestRule.waitUntilAtLeastOneExists(hasTestTag("PokemonItem"), 3000L)

		composeTestRule.onNodeWithText("Bulbasaur", ignoreCase = true).performClick()

		composeTestRule.waitUntilAtLeastOneExists(hasTestTag("PokemonDetails"), 3000L)

		composeTestRule.onAllNodesWithText(text = "Height", substring = true, useUnmergedTree = true)
			.assertAny(hasText("0", substring = true))

	}

}
