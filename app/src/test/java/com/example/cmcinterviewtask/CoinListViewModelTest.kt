package com.example.cmcinterviewtask

import com.example.cmcinterviewtask.data.dto.CoinsDto
import com.example.cmcinterviewtask.data.dto.Roi
import com.example.cmcinterviewtask.util.MainDispatcherRule
import com.example.democleanarchitecture.domain.use_cases.get_coins.GetCoinsUseCase
import com.example.democleanarchitecture.presentation.coin_list.CoinListViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class CoinListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var fakeRepository: FakeCoinRepository
    private lateinit var getCoinsUseCase: GetCoinsUseCase
    private lateinit var viewModel: CoinListViewModel

    @Before
    fun setup() {
        fakeRepository = FakeCoinRepository()
        getCoinsUseCase = GetCoinsUseCase(fakeRepository)
    }


    // TEST 1: Initial loading state
    @Test
    fun `initial state is loading`() = runTest {

        viewModel = CoinListViewModel(getCoinsUseCase)

        val state = viewModel.state.value

        assertThat(state.isLoading).isTrue()
        assertThat(state.coins).isEmpty()
        assertThat(state.error).isEmpty()
    }


    // TEST 2: Success state
    @Test
    fun `success updates coin list`() = runTest {

        fakeRepository.setCoins(
            listOf(
                CoinsDto(
                    ath = 69000.0,
                    ath_change_percentage = -10.5,
                    ath_date = "2021-11-10T14:24:11.849Z",
                    atl = 65.0,
                    atl_change_percentage = 150000.0,
                    atl_date = "2013-07-06T00:00:00.000Z",
                    circulating_supply = 19000000.0,
                    current_price = 100.0,
                    fully_diluted_valuation = 2100000000000,
                    high_24h = 105.0,
                    id = "btc",
                    image = "",
                    last_updated = "2025-01-01T10:00:00.000Z",
                    low_24h = 95.0,
                    market_cap = 1900000000000,
                    market_cap_change_24h = -5000000000.0,
                    market_cap_change_percentage_24h = -0.25,
                    market_cap_rank = 1,
                    max_supply = 21000000,
                    name = "Bitcoin",
                    price_change_24h = 2.0,
                    price_change_percentage_1h_in_currency = 0.1,
                    price_change_percentage_24h = 2.5,
                    roi = Roi(
                        times = 100.0,
                        currency = "usd",
                        percentage = 10000.0
                    ),
                    symbol = "BTC",
                    total_supply = 21000000.0,
                    total_volume = 35000000000.0
                )
            )
        )

        viewModel = CoinListViewModel(getCoinsUseCase)

        advanceUntilIdle()

        val state = viewModel.state.value

        assertThat(state.isLoading).isFalse()
        assertThat(state.error).isEmpty()
        assertThat(state.coins).hasSize(1)
        assertThat(state.coins.first().name).isEqualTo("Bitcoin")
    }


    // TEST 3: Error state
    @Test
    fun `error updates error state`() = runTest {

        fakeRepository.setShouldThrowError(true)

        viewModel = CoinListViewModel(getCoinsUseCase)

        advanceUntilIdle()

        val state = viewModel.state.value

        assertThat(state.isLoading).isFalse()
        assertThat(state.coins).isEmpty()
        assertThat(state.error).isEqualTo("Network error")
    }


    // TEST 4: Reload triggers loading again
    @Test
    fun `reload sets loading again`() = runTest {

        fakeRepository.setCoins(emptyList())

        viewModel = CoinListViewModel(getCoinsUseCase)

        advanceUntilIdle()

        viewModel.loadCoins()

        val state = viewModel.state.value

        assertThat(state.isLoading).isTrue()
    }
}