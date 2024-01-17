package com.example.cleanarchitecturecryptocurrencyapp.presentation.theme.presentation.coin_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitecturecryptocurrencyapp.common.Resource
import com.example.cleanarchitecturecryptocurrencyapp.domain.use_cases.get_coins.GetCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase
) : ViewModel() {
    private val _state = mutableStateOf<CoinListState>(CoinListState())
    val state: State<CoinListState> = _state

    init {
        getCoins()
    }
    private fun getCoins() {
        getCoinsUseCase().onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false, error = result.message ?: "An un expected error occurred"
                    )
                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = "",
                        coins = result.data ?: emptyList()
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}