package com.example.cmcinterviewtask.domain.model

data class Coins(
    val id: String,
    val image: String,
    val current_price: Double,
    val market_cap_change_percentage_24h: Double,
    val symbol: String,
    val name: String,
    val price_change_percentage_24h: Double
)
