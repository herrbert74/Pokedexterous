package com.zsoltbertalan.pokedexterous.data.db

import com.zsoltbertalan.pokedexterous.domain.model.Pokemon
import com.zsoltbertalan.pokedexterous.domain.model.PokemonItem
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class PokemonDbo() : RealmObject {

	constructor(
		id: String,
		name: String = "",
	) : this() {
		this.id = id
		this.name = name
	}

	@PrimaryKey
	var id: String = ""
	var name: String = ""

}

fun PokemonItem.toDbo(): PokemonDbo = PokemonDbo(
	id = this.id,
	name = this.name,
)

fun PokemonDbo.toPokemon(): PokemonItem = PokemonItem(
	id = this.id,
	name = this.name,
)
