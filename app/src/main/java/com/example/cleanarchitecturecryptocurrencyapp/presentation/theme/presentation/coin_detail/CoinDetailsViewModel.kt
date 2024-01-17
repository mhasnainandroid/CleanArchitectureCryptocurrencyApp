package com.example.cleanarchitecturecryptocurrencyapp.presentation.theme.presentation.coin_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitecturecryptocurrencyapp.common.Constants
import com.example.cleanarchitecturecryptocurrencyapp.common.Resource
import com.example.cleanarchitecturecryptocurrencyapp.domain.use_cases.get_coin.GetCoinUseCase
import com.example.cleanarchitecturecryptocurrencyapp.domain.use_cases.get_coins.GetCoinsUseCase
import com.example.cleanarchitecturecryptocurrencyapp.presentation.theme.presentation.coin_list.CoinListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinDetailsViewModel @Inject constructor(
    private val getCoinUseCase: GetCoinUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = mutableStateOf<CoinDetailState>(CoinDetailState())
    val state: State<CoinDetailState> = _state

    init {
        savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.let { coinId ->
            getCoin(coinId)
        }
    }

    private fun getCoin(coinId: String) {
        getCoinUseCase(coinId).onEach { result ->
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
                        coin = result.data
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}