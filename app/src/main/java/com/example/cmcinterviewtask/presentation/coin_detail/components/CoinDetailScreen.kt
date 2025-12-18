package com.example.cmcinterviewtask.presentation.coin_detail.components

import CoinDetailLoadingUI
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.cmcinterviewtask.R
import com.example.cmcinterviewtask.common.toCurrency
import com.example.cmcinterviewtask.domain.model.CoinDetail
import com.example.cmcinterviewtask.presentation.coin_detail.CoinDetailState
import com.example.cmcinterviewtask.presentation.coin_detail.CoinDetailViewModel

@Composable
fun CoinDetailScreen(coinDetailViewModel: CoinDetailViewModel) {

    val coinDetailState: CoinDetailState = coinDetailViewModel.state.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        if (coinDetailState.isLoading) {
            CoinDetailLoadingUI()
        } else if (!coinDetailState.error.trim().equals("")) {

        } else {

            if (coinDetailState.coins.isNotEmpty()) {
                CoinHeader(coinDetailState.coins[0])
                PriceSection(coinDetailState.coins[0])
                MarketStatsCard(coinDetailState.coins[0])
                AllTimeCard(coinDetailState.coins[0])
                SupplyCard(coinDetailState.coins[0])
            }
        }
    }

}


@Composable
fun CoinHeader(coin: CoinDetail) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            model = coin.image,
            contentDescription = coin.name,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = coin.name ?: "",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = coin.symbol?.uppercase() ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun PriceSection(coin: CoinDetail) {
    val isPositive = coin.price_change_percentage_24h?.let {
        if (it > 0) {
            true
        } else {
            false
        }
    }
    val color = if (isPositive == true) Color(0xFF2E7D32) else Color(0xFFC62828)
    var arrow = if (isPositive == true) R.drawable.ic_arrowup else R.drawable.ic_arrowdown
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color.Transparent)
    ) {

        Text(
            text = coin.current_price?.toCurrency() ?: "",
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painterResource(arrow),
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = "${coin.price_change_percentage_24h} %",
                color = color,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}


@Composable
fun InfoRow(title: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .background(Color.Transparent),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, color = Color.Gray)
        Text(text = value, fontWeight = FontWeight.SemiBold)
    }
}


@Composable
fun MarketStatsCard(coin: CoinDetail) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.Transparent),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = "Market Stats",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            InfoRow("Market Cap", "${coin.market_cap?.toCurrency()}")
            InfoRow("24h Volume", "${coin.total_volume?.toCurrency()}")
            InfoRow("Market Rank", "#${coin.market_cap_rank}")
        }
    }
}


@Composable
fun AllTimeCard(coin: CoinDetail) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color.Transparent),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = "All Time",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            InfoRow("All Time High", "${coin.ath?.toCurrency()}")
            InfoRow("ATH Date", coin?.ath_date ?: "")

            Spacer(modifier = Modifier.height(8.dp))

            InfoRow("All Time Low", "${coin.atl?.toCurrency()}")
            InfoRow("ATL Date", coin.atl_date ?: "")
        }
    }
}


@Composable
fun SupplyCard(coin: CoinDetail) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.Transparent),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = "Supply Information",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            InfoRow("Circulating Supply", coin.circulating_supply.toString())
            InfoRow("Total Supply", coin.total_supply.toString())
            InfoRow("Max Supply", coin.max_supply.toString())
        }
    }
}


