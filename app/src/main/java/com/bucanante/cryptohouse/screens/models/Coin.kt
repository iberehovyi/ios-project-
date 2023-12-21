package com.bucanante.cryptohouse.screens.models

data class Coin (
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    val current_price: Double
)