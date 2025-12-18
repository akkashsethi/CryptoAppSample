package com.example.cmcinterviewtask.domain.repository

import com.example.cmcinterviewtask.data.dto.CoinDetailDto
import com.example.cmcinterviewtask.data.dto.CoinsDto


interface CoinRepository {

    suspend fun getCoins(): List<CoinsDto>

    suspend fun getCoin(coinId: String): List<CoinDetailDto>

}