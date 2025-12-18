package com.example.cmcinterviewtask.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.democleanarchitecture.domain.use_cases.get_coins.GetCoinsUseCase
import com.example.democleanarchitecture.presentation.coin_list.CoinListViewModel

class CoinListViewModelFactory (private val getCoinsUseCase: GetCoinsUseCase): ViewModelProvider.Factory{


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CoinListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CoinListViewModel(getCoinsUseCase = getCoinsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}