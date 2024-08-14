package com.zsoltbertalan.pokedexterous.data.db

import com.zsoltbertalan.pokedexterous.common.util.runCatchingUnit
import com.zsoltbertalan.pokedexterous.domain.model.Pokemon
import com.zsoltbertalan.pokedexterous.domain.model.PokemonDetails
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PokemonDao @Inject constructor(private val realm: Realm) : PokemonDataSource {

	override suspend fun purgeDatabase() {
		realm.write {
			val pokemons = this.query(PokemonDbo::class).find()
			delete(pokemons)
		}
	}

	override suspend fun insertPokemons(pokemons: List<Pokemon>) {
		runCatchingUnit {
			realm.write {
				pokemons.map { copyToRealm(it.toDbo(), UpdatePolicy.ERROR) }
			}
		}
	}

	override suspend fun insertPokemonDetails(pokemonDetails: PokemonDetails) {
		runCatchingUnit {
			realm.write {
				copyToRealm(pokemonDetails.toDbo(), UpdatePolicy.ERROR)
			}
		}
	}

	override fun getPokemons(): Flow<List<Pokemon>?> {
		return realm.query(PokemonDbo::class).asFlow()
			.map { dbo -> dbo.list.map { it.toPokemon() }.takeIf { it.isNotEmpty() } }
	}

	override fun getPokemonDetails(id: String): Flow<PokemonDetails?> {
		return realm.query<PokemonDetailsDbo>("id = $0", id).asFlow()
			.map { flow -> flow.list.map { it.toPokemonDetails() }.firstOrNull() }
	}

}
