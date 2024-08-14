package com.zsoltbertalan.pokedexterous.ui

import androidx.paging.testing.asSnapshot
import com.zsoltbertalan.pokedexterous.common.testhelper.PokemonMother
import com.zsoltbertalan.pokedexterous.domain.api.PokedexterousRepository
import com.zsoltbertalan.pokedexterous.presentation.ui.pokemons.PokemonsViewModel
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class PokemonsViewModelTest {

	private val pokedexterousRepository = mockk<PokedexterousRepository>(relaxed = true)

	private lateinit var pokemonsViewModel: PokemonsViewModel

	private val dispatcher = StandardTestDispatcher()

	@Before
	fun setUp() {

		Dispatchers.setMain(dispatcher)
		pokemonsViewModel = PokemonsViewModel(pokedexterousRepository)

	}

	@After
	fun tearDown() {

		Dispatchers.resetMain()

	}

	@Test
	fun `when started then getPokemons is called and returns correct list`() = runTest {

		coVerify(exactly = 1) { pokedexterousRepository.getAllPokemons() }
		backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
			pokemonsViewModel.pokemonList.asSnapshot()[0] shouldBeEqualToComparingFields PokemonMother.createPokemonList()
		}

	}

}


