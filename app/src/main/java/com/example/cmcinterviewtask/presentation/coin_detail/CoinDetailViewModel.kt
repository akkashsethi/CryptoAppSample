package com.example.cmcinterviewtask.presentation.coin_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cmcinterviewtask.common.Resource
import com.example.democleanarchitecture.domain.use_cases.get_coin.GetCoinUseCase
import com.example.democleanarchitecture.domain.use_cases.get_coins.GetCoinsUseCase
import com.example.democleanarchitecture.presentation.coin_list.CoinListState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CoinDetailViewModel(private val id: String,private val getCoinUseCase: GetCoinUseCase): ViewModel() {



    private val _state = mutableStateOf<CoinDetailState>(CoinDetailState())
    val state: State<CoinDetailState> = _state


    init {
        _state.value = CoinDetailState(isLoading = true)

            getCoinUseCase(id).onEach { result ->

                when (result) {

                    is Resource.Success -> {
                        _state.value = CoinDetailState(coins = result.data ?: emptyList())
                    }

                    is Resource.Error -> {
                        _state.value =
                            CoinDetailState(error = result.msg ?: "An Unexpected Error Occured")
                    }

                    is Resource.Loading -> {
                        _state.value = CoinDetailState(isLoading = true)
                    }

                }


            }.launchIn(viewModelScope)

    }


}