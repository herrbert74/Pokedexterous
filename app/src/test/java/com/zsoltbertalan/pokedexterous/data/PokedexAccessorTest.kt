package com.zsoltbertalan.pokedexterous.data

import com.zsoltbertalan.pokedexterous.common.testhelper.PokemonDtoMother
import com.zsoltbertalan.pokedexterous.common.testhelper.PokemonMother
import com.zsoltbertalan.pokedexterous.data.db.PokemonDataSource
import com.zsoltbertalan.pokedexterous.data.network.PokedexService
import com.zsoltbertalan.pokedexterous.data.network.dto.toPokemonReply
import com.zsoltbertalan.pokedexterous.data.repository.PokedexterousAccessor
import com.zsoltbertalan.pokedexterous.domain.model.PagingReply
import io.kotest.matchers.equality.shouldBeEqualUsingFields
import io.kotest.matchers.equals.shouldBeEqual
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
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

		coEvery { api.getPokemonPage(any()) } returns apiResponse
		coEvery { dataSource.getPokemonPage(0) } returns flowOf(PagingReply(PokemonMother.createPokemonList(), false))
		coEvery { dataSource.insertPokemons(any(), any()) } returns Unit
		underTest = PokedexterousAccessor(
			api, dataSource
		)
	}

	@Test
	fun `should return pokemons from api`() = runTest {

		val result = underTest.getPokemonPage(0)
		result.first().value.pagingList shouldBeEqual apiResponse.toPokemonReply().pagingList

	}

}
