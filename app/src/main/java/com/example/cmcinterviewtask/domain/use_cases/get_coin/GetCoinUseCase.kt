package com.example.democleanarchitecture.domain.use_cases.get_coin

import android.net.http.HttpException
import com.example.cmcinterviewtask.common.Resource
import com.example.cmcinterviewtask.common.retryIO
import com.example.cmcinterviewtask.data.dto.toCoin
import com.example.cmcinterviewtask.data.dto.toCoinDetail
import com.example.cmcinterviewtask.domain.model.CoinDetail
import com.example.cmcinterviewtask.domain.model.Coins
import com.example.cmcinterviewtask.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class GetCoinUseCase(private val coinRepository: CoinRepository) {


    operator fun invoke(coinId: String): Flow<Resource<List<CoinDetail>>> = flow {

        try {

            emit(Resource.Loading(null))
            val coinDetail = retryIO(times = 3) {
                coinRepository.getCoin(coinId = coinId).map {
                    it.toCoinDetail()
                }
            }
            emit(Resource.Success(coinDetail))

        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server"))
        }

    }


}