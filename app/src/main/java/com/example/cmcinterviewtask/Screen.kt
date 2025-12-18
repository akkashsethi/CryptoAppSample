package com.example.cmcinterviewtask

sealed class Screen(val route: String) {
    object CoinListScreen : Screen("coin_list_screen")
    object CoinDetailScreen : Screen("coin_detail_screen/{id}") {

        fun create(id: String) = "coin_detail_screen/$id"
    }
}