package com.zsoltbertalan.pokedexterous.presentation.ui.pokemondetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zsoltbertalan.pokedexterous.domain.api.PokedexterousRepository
import com.zsoltbertalan.pokedexterous.domain.model.Failure
import com.zsoltbertalan.pokedexterous.domain.model.PokemonDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
	savedStateHandle: SavedStateHandle,
	private val pokedexRepository: PokedexterousRepository
) : ViewModel() {

	private val _state = MutableStateFlow(UiState())
	val state: StateFlow<UiState> = _state.asStateFlow()

	private val pokemonName: String = checkNotNull(savedStateHandle["pokemonName"])
	private val pokemonId: String = checkNotNull(savedStateHandle["pokemonId"])

	init {
		viewModelScope.launch {
			requestPokemonDetails()
		}
	}

	private fun requestPokemonDetails() {
		viewModelScope.launch {
			_state.update { it.copy(loading = true, pokemonDetails = PokemonDetails(name = pokemonName)) }
			pokedexRepository.getPokemonDetails(pokemonId).collect { result ->
				_state.update { uiState ->
					when {
						result.isOk -> {

							uiState.copy(
								pokemonDetails = result.value,
								loading = false,
								error = null,
							)
						}

						else -> uiState.copy(loading = false, error = result.error)
					}
				}

			}
		}
	}

	data class UiState(
		val loading: Boolean = false,
		val pokemonDetails: PokemonDetails = PokemonDetails(),
		val error: Failure? = null
	)

}
