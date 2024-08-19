package com.zsoltbertalan.pokedexterous.data.db

import com.zsoltbertalan.pokedexterous.domain.model.PageData
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class PokemonPageDbo() : RealmObject {

	constructor(
		page: Int,
		next: String = "",
		previous: String = "",
		count: Int,
	) : this() {
		this.page = page
		this.next = next
		this.previous = previous
		this.count = count
	}

	@PrimaryKey
	var page: Int = 0
	var next: String = ""
	var previous: String = ""
	var count: Int = 0

}

fun PageData.toDbo(): PokemonPageDbo = PokemonPageDbo(
	page, next ?: "", previous ?: "", count
)

fun PokemonPageDbo.toPageData(): PageData = PageData(
	page, next.ifEmpty { null }, previous.ifEmpty { null }, count
)
