package com.example.cmcinterviewtask.data.repository

import com.example.cmcinterviewtask.data.dto.CoinDetailDto
import com.example.cmcinterviewtask.data.dto.CoinsDto
import com.example.cmcinterviewtask.data.remote.CoinsApi
import com.example.cmcinterviewtask.domain.repository.CoinRepository


class CoinRespositoryImplementation(val coinsApi: CoinsApi): CoinRepository {


    override suspend fun getCoins(): List<CoinsDto> {
       return coinsApi.getCoins()
    }

    override suspend fun getCoin(coinId: String): List<CoinDetailDto> {
      return coinsApi.getCoin(coinId = coinId)
    }



}