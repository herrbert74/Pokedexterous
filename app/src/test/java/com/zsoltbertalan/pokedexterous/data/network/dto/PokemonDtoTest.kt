package com.zsoltbertalan.pokedexterous.data.network.dto

import com.zsoltbertalan.pokedexterous.common.testhelper.PokemonDtoMother
import com.zsoltbertalan.pokedexterous.domain.model.PokemonItem
import io.kotest.matchers.shouldBe
import org.junit.Before
import org.junit.Test

class PokemonDtoTest {

	private var mappedResponse :List<PokemonItem> = emptyList()

	@Before
	fun setup() {
		val responseDto = PokemonDtoMother.createPokemonDtoList()
		mappedResponse = responseDto.toPokemonReply().pagingList
	}

	@Test
	fun `when there is a response then name is mapped`() {
		mappedResponse[0].name shouldBe "name1"
	}

}
