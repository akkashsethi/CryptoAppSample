package com.example.cmcinterviewtask

import com.example.cmcinterviewtask.data.dto.CoinDetailDto
import com.example.cmcinterviewtask.data.dto.CoinsDto
import com.example.cmcinterviewtask.domain.repository.CoinRepository
import java.io.IOException

class FakeCoinRepository : CoinRepository {

    private var coins: List<CoinsDto> = emptyList()
    private var coinDetails: Map<String, List<CoinDetailDto>> = emptyMap()
    private var shouldThrowError = false

    // ---------- Controls for tests ----------

    fun setCoins(data: List<CoinsDto>) {
        coins = data
    }

    fun setCoinDetails(
        coinId: String,
        data: List<CoinDetailDto>
    ) {
        coinDetails = coinDetails + (coinId to data)
    }

    fun setShouldThrowError(value: Boolean) {
        shouldThrowError = value
    }

    fun reset() {
        coins = emptyList()
        coinDetails = emptyMap()
        shouldThrowError = false
    }

    // ---------- Repository methods ----------

    override suspend fun getCoins(): List<CoinsDto> {
        if (shouldThrowError) {
            throw IOException("Network error")
        }
        return coins
    }

    override suspend fun getCoin(coinId: String): List<CoinDetailDto> {
        if (shouldThrowError) {
            throw IOException("Couldn't reach server")
        }
        return coinDetails[coinId] ?: emptyList()
    }
}