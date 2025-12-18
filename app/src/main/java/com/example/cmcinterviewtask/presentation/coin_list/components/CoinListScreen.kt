package com.example.democleanarchitecture.presentation.coin_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cmcinterviewtask.Screen
import com.example.cmcinterviewtask.presentation.coin_list.components.CoinListLoading
import com.example.democleanarchitecture.presentation.coin_list.CoinListViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinListScreen(
    viewModel: CoinListViewModel,
    navController: NavController
) {


    val state = viewModel.state.value

    val pullToRefreshState = rememberPullToRefreshState()
    PullToRefreshBox(
        isRefreshing = state.isLoading,
        onRefresh = { viewModel.loadCoins() },
        state = pullToRefreshState
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {

            when {
                state.isLoading && state.coins.isEmpty() -> {
                    CoinListLoading()
                }

                state.error.isNotBlank() -> {
                    Text(
                        text = state.error,
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.error
                    )
                }

                else -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(state.coins) { coin ->
                            CoinListItem(coins = coin) {

                                navController.navigate(Screen.CoinDetailScreen.create(coin.id))
                            }
                        }
                    }
                }
            }


        }
    }
}