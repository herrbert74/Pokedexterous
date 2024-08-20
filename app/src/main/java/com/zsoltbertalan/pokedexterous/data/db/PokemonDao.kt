package com.zsoltbertalan.pokedexterous.data.db

import com.zsoltbertalan.pokedexterous.common.async.IoDispatcher
import com.zsoltbertalan.pokedexterous.common.util.runCatchingUnit
import com.zsoltbertalan.pokedexterous.domain.model.PageData
import com.zsoltbertalan.pokedexterous.domain.model.PagingReply
import com.zsoltbertalan.pokedexterous.domain.model.PokemonDetails
import com.zsoltbertalan.pokedexterous.domain.model.PokemonItem
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PokemonDao @Inject constructor(
	private val realm: Realm,
	@IoDispatcher private val ioContext: CoroutineDispatcher,
) : PokemonDataSource {

	override suspend fun purgeDatabase() {
		realm.write {
			val pokemons = this.query(PokemonDbo::class).find()
			delete(pokemons)
		}
	}

	override suspend fun insertPokemons(pokemons: List<PokemonItem>, page: Int) {
		runCatchingUnit {
			realm.write {
				pokemons.map { copyToRealm(it.toDbo(page), UpdatePolicy.ALL) }
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

	override fun getPokemonPage(page: Int): Flow<PagingReply<PokemonItem>?> {
		return realm.query(PokemonDbo::class, "page = $0", page).asFlow()
			.map { change ->
				val pageData = getPageData(page)
				val isLastPage = pageData?.next.isNullOrEmpty()
				val pagingList = change.list.map { it.toPokemon() }.ifEmpty { null }
				pagingList?.let {
					PagingReply(pagingList, isLastPage)
				}
			}.flowOn(ioContext)
	}

	override fun getPokemonDetails(id: String): Flow<PokemonDetails?> {
		return realm.query<PokemonDetailsDbo>("id = $0", id).asFlow()
			.map { flow -> flow.list.map { it.toPokemonDetails() }.firstOrNull() }.flowOn(ioContext)
	}

	override suspend fun insertPageData(page: PageData) {
		runCatchingUnit {
			realm.write {
				copyToRealm(page.toDbo(), UpdatePolicy.ALL)
			}
		}
	}

	private fun getPageData(page: Int): PageData? {
		return realm.query(PokemonPageDbo::class, "page = $0", page).find()
			.map { dbo -> dbo.toPageData() }.firstOrNull()
	}

}
