package com.bucanante.cryptohouse.screens.apiService

import com.bucanante.cryptohouse.screens.models.Coin
import com.bucanante.cryptohouse.screens.models.TrendingCoinsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinGeckoApiService {
    @GET("coins/markets")
    suspend fun getCoins(
        @Query("vs_currency") vsCurrency: String = "usd",
        @Query("order") order: String = "market_cap_desc",
        @Query("per_page") perPage: Int = 100,
        @Query("page") page: Int = 1,
        @Query("sparkline") sparkline: Boolean = false,
        @Query("price_change_percentage") priceChangePercentage: String? = null
    ): List<Coin>
    @GET("search/trending")
    suspend fun getTrendingCoins(): TrendingCoinsResponse
}