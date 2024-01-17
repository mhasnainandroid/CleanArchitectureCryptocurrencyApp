package com.example.cleanarchitecturecryptocurrencyapp.presentation.theme.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cleanarchitecturecryptocurrencyapp.presentation.theme.presentation.Screen
import com.example.cleanarchitecturecryptocurrencyapp.presentation.theme.presentation.coin_detail.CoinDetailScreen
import com.example.cleanarchitecturecryptocurrencyapp.presentation.theme.presentation.coin_detail.CoinDetailsViewModel
import com.example.cleanarchitecturecryptocurrencyapp.presentation.theme.presentation.coin_list.CoinListScreen
import com.example.cleanarchitecturecryptocurrencyapp.presentation.theme.presentation.coin_list.CoinListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CleanArchitectureCryptocurrencyAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.CoinListScreen.route
                    ) {
                        composable(route = Screen.CoinListScreen.route) {
                            val coinListViewModel: CoinListViewModel = hiltViewModel()
                            CoinListScreen(
                                state = coinListViewModel.state.value,
                                navController = navController
                            )
                        }
                        composable(Screen.CoinDetailScreen.route+"/{coinId}") {
                            val coinDetailsViewModel: CoinDetailsViewModel = hiltViewModel()
                            CoinDetailScreen(state = coinDetailsViewModel.state.value)
                        }
                    }
                }
            }
        }
    }
}
