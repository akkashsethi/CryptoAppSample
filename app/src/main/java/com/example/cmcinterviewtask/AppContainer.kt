package com.example.cmcinterviewtask

import com.example.cmcinterviewtask.common.Constants
import com.example.cmcinterviewtask.data.remote.CoinsApi
import com.example.cmcinterviewtask.data.repository.CoinRespositoryImplementation
import com.example.cmcinterviewtask.domain.repository.CoinRepository
import com.example.democleanarchitecture.domain.use_cases.get_coin.GetCoinUseCase
import com.example.democleanarchitecture.domain.use_cases.get_coins.GetCoinsUseCase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer {



    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val coinsApi: CoinsApi by lazy {
        retrofit.create(CoinsApi::class.java)
    }


    private val coinRepository: CoinRepository by lazy {
        CoinRespositoryImplementation(coinsApi)
    }


    val getCoinsUseCase: GetCoinsUseCase by lazy {
        GetCoinsUseCase(coinRepository)
    }


    val getCoinUseCase: GetCoinUseCase by lazy {
        GetCoinUseCase(coinRepository)
    }
}