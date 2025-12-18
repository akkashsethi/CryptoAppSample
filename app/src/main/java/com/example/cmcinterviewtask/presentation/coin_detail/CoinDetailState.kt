package com.example.cmcinterviewtask.presentation.coin_detail

import com.example.cmcinterviewtask.domain.model.CoinDetail
import com.example.cmcinterviewtask.domain.model.Coins

data class CoinDetailState(
    val isLoading: Boolean = false,
    val coins: List<CoinDetail> = emptyList(),
    val error: String = ""
)
