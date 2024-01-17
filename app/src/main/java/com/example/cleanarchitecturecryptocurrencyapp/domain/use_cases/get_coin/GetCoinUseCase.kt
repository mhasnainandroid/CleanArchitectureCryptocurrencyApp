package com.example.cleanarchitecturecryptocurrencyapp.domain.use_cases.get_coin

import com.example.cleanarchitecturecryptocurrencyapp.common.Resource
import com.example.cleanarchitecturecryptocurrencyapp.data.remote.dto.toCoin
import com.example.cleanarchitecturecryptocurrencyapp.data.remote.dto.toCoinDetails
import com.example.cleanarchitecturecryptocurrencyapp.domain.model.Coin
import com.example.cleanarchitecturecryptocurrencyapp.domain.model.CoinDetail
import com.example.cleanarchitecturecryptocurrencyapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(private val repository: CoinRepository) {
    operator fun invoke(coinId:String):Flow<Resource<CoinDetail>> = flow {
        try {
            emit(Resource.Loading())
            val coinDetails = repository.getCoinsById(coinId = coinId)
            emit(Resource.Success(coinDetails.toCoinDetails()))
        } catch (e:HttpException){
            emit(Resource.Error(message = e.localizedMessage?:"An en expected error occurred"))
        }
        catch (e: IOException){
            emit(Resource.Error(message = "Could\'nt reach server. Check your internet connection."))
        }
    }
}