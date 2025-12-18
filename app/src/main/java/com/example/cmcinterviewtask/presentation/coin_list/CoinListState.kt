package com.example.democleanarchitecture.presentation.coin_list

import com.example.cmcinterviewtask.domain.model.Coins


data class CoinListState(
    val isLoading: Boolean = false,
    val coins: List<Coins> = emptyList(),
    val error: String = ""
)
