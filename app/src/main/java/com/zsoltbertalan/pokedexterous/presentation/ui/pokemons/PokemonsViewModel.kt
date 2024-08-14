package com.zsoltbertalan.pokedexterous.presentation.ui.pokemons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.zsoltbertalan.pokedexterous.domain.api.PokedexterousRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonsViewModel @Inject constructor(private val pokedexterousRepository: PokedexterousRepository) : ViewModel() {

	val pokemonList = pokedexterousRepository.getAllPokemons().cachedIn(viewModelScope)

}
