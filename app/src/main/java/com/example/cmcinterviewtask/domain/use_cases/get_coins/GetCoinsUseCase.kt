package com.example.democleanarchitecture.domain.use_cases.get_coins

import android.net.http.HttpException
import com.example.cmcinterviewtask.common.Resource
import com.example.cmcinterviewtask.common.retryIO
import com.example.cmcinterviewtask.data.dto.toCoin
import com.example.cmcinterviewtask.domain.model.Coins
import com.example.cmcinterviewtask.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

open class GetCoinsUseCase (private val coinRepository: CoinRepository) {


    open operator fun invoke(): Flow<Resource<List<Coins>>> = flow {

        try{

            emit(Resource.Loading(null))


            val coins = retryIO(times = 3) {
                coinRepository.getCoins().map {
                    it.toCoin()
                }
            }
            emit(Resource.Success(coins))

        }catch (e: HttpException){
            emit(Resource.Error(message = e.localizedMessage))
        }catch (e: IOException){
            emit(Resource.Error(message = "Couldn't reach server"))
        }

    }


}