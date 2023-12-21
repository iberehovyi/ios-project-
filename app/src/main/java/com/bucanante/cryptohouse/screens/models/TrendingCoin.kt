package com.bucanante.cryptohouse.screens.models

data class TrendingCoinsResponse (
    val coins: List<TrendingCoinItem>
)

data class TrendingCoinItem (
    val item: Coin
)