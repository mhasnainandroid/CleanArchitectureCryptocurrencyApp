package com.example.cleanarchitecturecryptocurrencyapp.presentation.theme.presentation.coin_detail

import com.example.cleanarchitecturecryptocurrencyapp.domain.model.Coin
import com.example.cleanarchitecturecryptocurrencyapp.domain.model.CoinDetail

data class CoinDetailState(
    val isLoading:Boolean = false,
    val coin:CoinDetail? = null,
    val error:String = ""
)
