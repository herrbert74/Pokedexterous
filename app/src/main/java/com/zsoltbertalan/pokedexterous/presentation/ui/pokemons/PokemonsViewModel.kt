package com.zsoltbertalan.pokedexterous.presentation.ui.pokemons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zsoltbertalan.pokedexterous.domain.api.PokedexterousRepository
import com.zsoltbertalan.pokedexterous.domain.model.Failure
import com.zsoltbertalan.pokedexterous.domain.model.PokemonItem
import com.zsoltbertalan.pokedexterous.presentation.component.paging.PaginationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonsViewModel @Inject constructor(
	private val pokedexterousRepository: PokedexterousRepository
) : ViewModel() {

	val paginationState = PaginationState<Int, PokemonItem>(
		initialPageKey = 0,
		onRequestPage = {
			loadPage(it)
		}
	)

	private fun loadPage(pageKey: Int) {

		viewModelScope.launch {
			pokedexterousRepository.getPokemonPage(page = pageKey).collect {
				when {
					it.isOk -> {
						paginationState.appendPage(
							items = it.value.pagingList,
							nextPageKey = if(it.value.isLastPage) -1 else pageKey + 1,
							isLastPage = it.value.isLastPage
						)
					}

					else -> {
						val e = it.error as? Failure.ServerError
						paginationState.setError(Exception(e?.message))
					}

				}
			}
		}
	}
}