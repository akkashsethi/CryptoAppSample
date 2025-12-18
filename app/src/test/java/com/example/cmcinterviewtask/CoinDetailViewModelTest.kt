package com.example.cmcinterviewtask

import com.example.cmcinterviewtask.data.dto.CoinDetailDto
import com.example.cmcinterviewtask.presentation.coin_detail.CoinDetailViewModel
import com.example.cmcinterviewtask.util.MainDispatcherRule
import com.example.democleanarchitecture.domain.use_cases.get_coin.GetCoinUseCase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CoinDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var fakeRepository: FakeCoinRepository
    private lateinit var getCoinUseCase: GetCoinUseCase
    private lateinit var viewModel: CoinDetailViewModel

    private val coinId = "bitcoin"

    @Before
    fun setup() {
        fakeRepository = FakeCoinRepository()
        getCoinUseCase = GetCoinUseCase(fakeRepository)
    }

    // TEST 1: Initial loading state
    @Test
    fun `initial state is loading`() = runTest {

        viewModel = CoinDetailViewModel(
            id = coinId,
            getCoinUseCase = getCoinUseCase
        )

        val state = viewModel.state.value

        assertThat(state.isLoading).isTrue()
        assertThat(state.coins).isEmpty()
        assertThat(state.error).isEmpty()
    }

    // TEST 2: Success state
    @Test
    fun `success updates coin detail state`() = runTest {

        fakeRepository.setCoinDetails(
            coinId,
            listOf(
                CoinDetailDto(
                    id = coinId,
                    name = "Bitcoin",
                    symbol = "BTC",
                    image = "",
                    current_price = 100.0,
                    ath = 200.0,
                    atl = 50.0,
                    ath_date = "",
                    atl_date = "",
                    ath_change_percentage = 0.0,
                    atl_change_percentage = 0.0,
                    circulating_supply = 0.0,
                    total_supply = 0.0,
                    max_supply = 0.0,
                    market_cap = 0,
                    market_cap_rank = 1.0,
                    market_cap_change_24h = 0.0,
                    market_cap_change_percentage_24h = 0.0,
                    fully_diluted_valuation = 0,
                    high_24h = 0.0,
                    low_24h = 0.0,
                    total_volume = 0,
                    last_updated = "",
                    roi = null,
                    price_change_24h = 0.0,
                    price_change_percentage_24h = 0.0
                )
            )
        )

        viewModel = CoinDetailViewModel(
            id = coinId,
            getCoinUseCase = getCoinUseCase
        )

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

        viewModel = CoinDetailViewModel(
            id = coinId,
            getCoinUseCase = getCoinUseCase
        )

        advanceUntilIdle()

        val state = viewModel.state.value

        assertThat(state.isLoading).isFalse()
        assertThat(state.coins).isEmpty()
        assertThat(state.error).isEqualTo("Couldn't reach server")
    }
}