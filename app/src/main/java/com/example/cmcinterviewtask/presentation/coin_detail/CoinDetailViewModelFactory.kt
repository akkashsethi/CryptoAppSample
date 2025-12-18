package com.example.cmcinterviewtask.presentation.coin_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.democleanarchitecture.domain.use_cases.get_coin.GetCoinUseCase
import com.example.democleanarchitecture.domain.use_cases.get_coins.GetCoinsUseCase
import com.example.democleanarchitecture.presentation.coin_list.CoinListViewModel

class CoinDetailViewModelFactory (private val id: String,private val getCoinUseCase: GetCoinUseCase): ViewModelProvider.Factory{


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CoinDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CoinDetailViewModel(id=id, getCoinUseCase = getCoinUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}