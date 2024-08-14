package com.zsoltbertalan.pokedexterous.data

import androidx.paging.testing.asSnapshot
import com.zsoltbertalan.pokedexterous.common.testhelper.PokemonDtoMother
import com.zsoltbertalan.pokedexterous.common.testhelper.PokemonMother
import com.zsoltbertalan.pokedexterous.data.db.PokemonDataSource
import com.zsoltbertalan.pokedexterous.data.network.PokedexService
import com.zsoltbertalan.pokedexterous.data.network.dto.toPokemonList
import com.zsoltbertalan.pokedexterous.data.repository.PokedexterousAccessor
import io.kotest.matchers.equality.shouldBeEqualUsingFields
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

internal class PokedexAccessorTest {

	private lateinit var underTest: PokedexterousAccessor

	private var api = mockk<PokedexService>()
	private var dataSource = mockk<PokemonDataSource>()

	private val apiResponse = PokemonDtoMother.createPokemonDtoList()


	@Before
	fun setUp() {

		coEvery { api.getPokemons(any()) } returns apiResponse
		coEvery { dataSource.getPokemons() } returns flowOf(PokemonMother.createPokemonList())
		coEvery { dataSource.insertPokemons(any()) } returns Unit
		underTest = PokedexterousAccessor(
			api, dataSource
		)
	}

	@Test
	fun `should return pokemons from api`() = runTest {


		val result = underTest.getAllPokemons()
		result.asSnapshot()[0] shouldBeEqualUsingFields apiResponse.toPokemonList()[0]

	}

}
