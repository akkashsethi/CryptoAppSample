package com.example.cmcinterviewtask.data.remote

import com.example.cmcinterviewtask.common.Constants
import com.example.cmcinterviewtask.data.dto.CoinDetailDto
import com.example.cmcinterviewtask.data.dto.CoinsDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CoinsApi {

    @GET("api/v3/coins/markets")
    suspend fun getCoins(
        @Query("vs_currency") vsCurrency: String = Constants.CURRENCY,
        @Query("category") category: String = "layer-1",
        @Query("price_change_percentage") priceChangePercentage: String = "1h",
        @Query("x-cg-demo-api-key") apiKey: String = "CG-wb2tKh4A9Da55AEiJPJ4N1aM"
    ): List<CoinsDto>

    @GET("api/v3/coins/markets")
    suspend fun getCoin(
        @Query("ids") coinId: String,
        @Query("vs_currency") vsCurrency: String = Constants.CURRENCY,
        @Query("include_platform") includePlatform: Boolean = false,
        @Header("x-cg-demo-api-key") apiKey: String = "CG-wb2tKh4A9Da55AEiJPJ4N1aM"
    ): List<CoinDetailDto>





}