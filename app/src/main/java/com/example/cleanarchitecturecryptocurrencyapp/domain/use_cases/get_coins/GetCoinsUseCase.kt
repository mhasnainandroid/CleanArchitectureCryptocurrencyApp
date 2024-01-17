package com.example.cleanarchitecturecryptocurrencyapp.domain.use_cases.get_coins

import com.example.cleanarchitecturecryptocurrencyapp.common.Resource
import com.example.cleanarchitecturecryptocurrencyapp.data.remote.dto.toCoin
import com.example.cleanarchitecturecryptocurrencyapp.domain.model.Coin
import com.example.cleanarchitecturecryptocurrencyapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(private val repository: CoinRepository) {
    operator fun invoke():Flow<Resource<List<Coin>>> = flow {
        try {
            emit(Resource.Loading())
            val coins = repository.getCoins()
            emit(Resource.Success(coins.map { it.toCoin() }))
        } catch (e:HttpException){
            emit(Resource.Error(message = e.localizedMessage?:"An en expected error occurred"))
        }
        catch (e: IOException){
            emit(Resource.Error(message = "Could\'nt reach server. Check your internet connection."))
        }
    }
}