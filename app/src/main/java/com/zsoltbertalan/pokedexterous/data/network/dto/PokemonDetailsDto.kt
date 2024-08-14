package com.zsoltbertalan.pokedexterous.data.network.dto

import com.zsoltbertalan.pokedexterous.domain.model.PokemonDetails

@Suppress("PropertyName")
data class PokemonDetailsDto(
	val abilities: List<Ability?>? = null,
	val base_experience: Int? = null,
	val cries: Cries? = null,
	val forms: List<Form?>? = null,
	val game_indices: List<GameIndice?>? = null,
	val height: Int? = null,
	val held_items: List<Any?>? = null,
	val id: Int? = null,
	val is_default: Boolean? = null,
	val location_area_encounters: String? = null,
	val moves: List<Move?>? = null,
	val name: String? = null,
	val order: Int? = null,
	val past_abilities: List<Any?>? = null,
	val past_types: List<Any?>? = null,
	val species: Species? = null,
	val sprites: Sprites? = null,
	val stats: List<Stat?>? = null,
	val types: List<Type?>? = null,
	val weight: Int? = null
) {
	data class Ability(
		val ability: Ability? = null,
		val is_hidden: Boolean? = null,
		val slot: Int? = null
	) {
		data class Ability(
			val name: String? = null,
			val url: String? = null
		)
	}

	data class Cries(
		val latest: String? = null,
		val legacy: String? = null
	)

	data class Form(
		val name: String? = null,
		val url: String? = null
	)

	data class GameIndice(
		val game_index: Int? = null,
		val version: Version? = null
	) {
		data class Version(
			val name: String? = null,
			val url: String? = null
		)
	}

	data class Move(
		val move: Move? = null,
		val version_group_details: List<VersionGroupDetail?>? = null
	) {
		data class Move(
			val name: String? = null,
			val url: String? = null
		)

		data class VersionGroupDetail(
			val level_learned_at: Int? = null,
			val move_learn_method: MoveLearnMethod? = null,
			val version_group: VersionGroup? = null
		) {
			data class MoveLearnMethod(
				val name: String? = null,
				val url: String? = null
			)

			data class VersionGroup(
				val name: String? = null,
				val url: String? = null
			)
		}
	}

	data class Species(
		val name: String? = null,
		val url: String? = null
	)

	data class Sprites(
		val other: Other? = null,
	) {
		data class Other(
			val `official-artwork`: OfficialArtwork? = null,
		) {
			data class OfficialArtwork(
				val front_default: String? = null,
				val front_shiny: String? = null
			)
		}
	}

	data class Stat(
		val base_stat: Int? = null,
		val effort: Int? = null,
		val stat: Stat? = null
	) {
		data class Stat(
			val name: String? = null,
			val url: String? = null
		)
	}

	data class Type(
		val slot: Int? = null,
		val type: Type? = null
	) {
		data class Type(
			val name: String? = null,
			val url: String? = null
		)
	}
}

fun PokemonDetailsDto.toPokemonDetails(): PokemonDetails = PokemonDetails(
	this.id ?: 0,
	this.name ?: "",
	this.sprites?.other?.`official-artwork`?.front_shiny ?: "",
	this.height ?: 0
)