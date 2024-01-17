package com.example.cleanarchitecturecryptocurrencyapp.presentation.theme.presentation

sealed class Screen (val route:String){
    data object CoinListScreen:Screen("coin_list")
    data object CoinDetailScreen:Screen("coin_details")
}