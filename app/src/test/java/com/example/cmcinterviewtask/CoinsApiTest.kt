package com.example.cmcinterviewtask

import com.example.cmcinterviewtask.common.Constants
import com.example.cmcinterviewtask.data.remote.CoinsApi
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalCoroutinesApi::class)
class CoinsApiTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: CoinsApi

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url(Constants.BASE_URL))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinsApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }


    @Test
    fun `getCoins returns parsed list`() = runTest {

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(
                """
            [
              {
                "ath": 69000.0,
                "ath_change_percentage": -10.0,
                "ath_date": "2021-11-10",
                "atl": 65.0,
                "atl_change_percentage": 150000.0,
                "atl_date": "2013-07-06",
                "circulating_supply": 19000000,
                "current_price": 100.0,
                "fully_diluted_valuation": 2100000000000,
                "high_24h": 105.0,
                "id": "bitcoin",
                "image": "",
                "last_updated": "",
                "low_24h": 95.0,
                "market_cap": 1900000000000,
                "market_cap_change_24h": 0.0,
                "market_cap_change_percentage_24h": 0.0,
                "market_cap_rank": 1,
                "max_supply": 21000000,
                "name": "Bitcoin",
                "price_change_24h": 2.0,
                "price_change_percentage_1h_in_currency": 0.1,
                "price_change_percentage_24h": 2.5,
                "roi": {
                  "times": 100,
                  "currency": "usd",
                  "percentage": 10000
                },
                "symbol": "btc",
                "total_supply": 21000000,
                "total_volume": 35000000000
              }
            ]
            """.trimIndent()
            )

        mockWebServer.enqueue(mockResponse)

        val result = api.getCoins()

        assertThat(result).hasSize(1)
        assertThat(result[0].id).isEqualTo("bitcoin")
        assertThat(result[0].name).isEqualTo("Bitcoin")
        assertThat(result[0].current_price).isEqualTo(100.0)
    }


    @Test
    fun `getCoin returns parsed coin detail`() = runTest {

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(
                """
            [
              {
                "ath": 69000.0,
                "ath_change_percentage": -10.0,
                "ath_date": "2021-11-10",
                "atl": 65.0,
                "atl_change_percentage": 150000.0,
                "atl_date": "2013-07-06",
                "circulating_supply": 19000000,
                "current_price": 100.0,
                "fully_diluted_valuation": 2100000000000,
                "high_24h": 105.0,
                "id": "bitcoin",
                "image": "",
                "last_updated": "",
                "low_24h": 95.0,
                "market_cap": 1900000000000,
                "market_cap_change_24h": 0.0,
                "market_cap_change_percentage_24h": 0.0,
                "market_cap_rank": 1,
                "max_supply": 21000000,
                "name": "Bitcoin",
                "price_change_24h": 2.0,
                "price_change_percentage_24h": 2.5,
                "roi": null,
                "symbol": "btc",
                "total_supply": 21000000,
                "total_volume": 35000000000
              }
            ]
            """.trimIndent()
            )

        mockWebServer.enqueue(mockResponse)

        val result = api.getCoin(coinId = "bitcoin")

        assertThat(result).hasSize(1)
        assertThat(result[0].id).isEqualTo("bitcoin")
        assertThat(result[0].name).isEqualTo("Bitcoin")
        assertThat(result[0].current_price).isEqualTo(100.0)
    }


    @Test
    fun `getCoin sends correct query params`() = runTest {

        mockWebServer.enqueue(MockResponse().setBody("[]"))

        api.getCoin(coinId = "bitcoin")

        val request = mockWebServer.takeRequest()

        assertThat(request.requestUrl?.queryParameter("ids"))
            .isEqualTo("bitcoin")

        assertThat(request.requestUrl?.queryParameter("vs_currency"))
            .isEqualTo("usd")
    }


}