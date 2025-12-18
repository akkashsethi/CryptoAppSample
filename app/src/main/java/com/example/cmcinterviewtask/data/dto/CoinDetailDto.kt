package com.example.cmcinterviewtask.data.dto

import com.example.cmcinterviewtask.domain.model.CoinDetail
import com.example.cmcinterviewtask.domain.model.Coins
import kotlin.Double

data class CoinDetailDto(
    val ath: Double,
    val ath_change_percentage: Double,
    val ath_date: String,
    val atl: Double,
    val atl_change_percentage: Double,
    val atl_date: String,
    val circulating_supply: Double,
    val current_price: Double,
    val fully_diluted_valuation: Long,
    val high_24h: Double,
    val id: String,
    val image: String,
    val last_updated: String,
    val low_24h: Double,
    val market_cap: Long,
    val market_cap_change_24h: Double,
    val market_cap_change_percentage_24h: Double,
    val market_cap_rank: Double,
    val max_supply: Double,
    val name: String,
    val price_change_24h: Double,
    val price_change_percentage_24h: Double,
    val roi: Any?,
    val symbol: String,
    val total_supply: Double,
    val total_volume: Long
)

fun CoinDetailDto.toCoinDetail(): CoinDetail {

    return CoinDetail(
        ath = ath,
        ath_change_percentage = ath_change_percentage,
        ath_date = ath_date,
        atl = atl,
        atl_change_percentage = atl_change_percentage,
        atl_date = atl_date,
        circulating_supply = circulating_supply,
        current_price = current_price,
        fully_diluted_valuation = fully_diluted_valuation,
        high_24h = high_24h,
        id = id,
        image = image,
        last_updated = last_updated,
        low_24h = low_24h,
        market_cap = market_cap,
        market_cap_change_24h = market_cap_change_24h,
        market_cap_change_percentage_24h = market_cap_change_percentage_24h,
        market_cap_rank = market_cap_rank,
        max_supply = max_supply,
        name = name,
        price_change_24h = price_change_24h,
        price_change_percentage_24h = price_change_percentage_24h,
        roi = roi,
        symbol = symbol,
        total_supply = total_supply,
        total_volume = total_volume
    )

}