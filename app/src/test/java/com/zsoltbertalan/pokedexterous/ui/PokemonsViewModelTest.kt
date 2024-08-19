package com.zsoltbertalan.pokedexterous.ui

import com.github.michaelbull.result.Ok
import com.zsoltbertalan.pokedexterous.common.testhelper.PokemonMother
import com.zsoltbertalan.pokedexterous.domain.api.PokedexterousRepository
import com.zsoltbertalan.pokedexterous.domain.model.PagingReply
import com.zsoltbertalan.pokedexterous.presentation.ui.pokemons.PokemonsViewModel
import io.kotest.matchers.equals.shouldBeEqual
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class PokemonsViewModelTest {

	private val pokedexterousRepository = mockk<PokedexterousRepository>()

	private lateinit var pokemonsViewModel: PokemonsViewModel

	private val dispatcher = StandardTestDispatcher()

	@Before
	fun setUp() {

		Dispatchers.setMain(dispatcher)
		pokemonsViewModel = PokemonsViewModel(pokedexterousRepository)
		coEvery { pokedexterousRepository.getPokemonPage(any()) } returns flowOf(
			Ok(PagingReply(PokemonMother.createPokemonList(), false))
		)

	}

	@After
	fun tearDown() {

		Dispatchers.resetMain()

	}

	@Test
	fun `when started then getPokemons is called and returns correct list`() = runTest {

		pokemonsViewModel.paginationState.onRequestPage(pokemonsViewModel.paginationState, 0)

		advanceUntilIdle()
		coVerify(exactly = 1) { pokedexterousRepository.getPokemonPage(any()) }
		backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
			pokemonsViewModel.paginationState.allItems shouldBeEqual PokemonMother.createPokemonList()
		}

	}

}


