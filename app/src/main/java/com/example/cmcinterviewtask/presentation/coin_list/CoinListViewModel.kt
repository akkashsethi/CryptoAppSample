package com.example.democleanarchitecture.presentation.coin_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cmcinterviewtask.common.Resource
import com.example.democleanarchitecture.domain.use_cases.get_coins.GetCoinsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CoinListViewModel  (private val getCoinsUseCase: GetCoinsUseCase) :
    ViewModel() {

    private val _state = mutableStateOf<CoinListState>(CoinListState())
    val state: State<CoinListState> = _state


    init {
      loadCoins()
    }



    fun loadCoins(){
        _state.value = CoinListState(isLoading = true)

            getCoinsUseCase().onEach { result ->

                when (result) {

                    is Resource.Success -> {
                        _state.value = CoinListState(coins = result.data ?: emptyList())
                    }

                    is Resource.Error -> {
                        _state.value =
                            CoinListState(error = result.msg ?: "An Unexpected Error Occured")
                    }

                    is Resource.Loading -> {
                        _state.value = CoinListState(isLoading = true)
                    }

                }


            }.launchIn(viewModelScope)

    }


}