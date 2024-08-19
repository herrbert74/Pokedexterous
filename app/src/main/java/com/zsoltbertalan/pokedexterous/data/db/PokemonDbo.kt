package com.zsoltbertalan.pokedexterous.data.db

import com.zsoltbertalan.pokedexterous.domain.model.PokemonItem
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class PokemonDbo() : RealmObject {

	constructor(
		id: String,
		name: String = "",
		page: Int = 0,
		) : this() {
		this.id = id
		this.name = name
		this.page = page
	}

	@PrimaryKey
	var id: String = ""
	var name: String = ""
	var page: Int = 0

}

fun PokemonItem.toDbo(page:Int): PokemonDbo = PokemonDbo(
	id = this.id,
	name = this.name,
	page = page,
)

fun PokemonDbo.toPokemon(): PokemonItem = PokemonItem(
	id = this.id,
	name = this.name,
)
