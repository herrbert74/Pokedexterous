package com.zsoltbertalan.pokedexterous.data.db

import com.zsoltbertalan.pokedexterous.domain.model.PokemonDetails
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class PokemonDetailsDbo() : RealmObject {

	constructor(
		id: Int,
		name: String,
		imageUrl: String = "",
		height: Int = 0
	) : this() {
		this.id = id
		this.name = name
		this.imageUrl = imageUrl
		this.height = height
	}

	@PrimaryKey
	var id: Int = 0
	var name: String = ""
	var imageUrl: String = ""
	var height: Int = 0

}

fun PokemonDetails.toDbo(): PokemonDetailsDbo = PokemonDetailsDbo(
	id = this.id,
	name = this.name,
	imageUrl = this.imageUrl,
	height = this.height,
)

fun PokemonDetailsDbo.toPokemonDetails(): PokemonDetails = PokemonDetails(
	id = this.id,
	name = this.name,
	imageUrl = this.imageUrl,
	height = this.height,
)