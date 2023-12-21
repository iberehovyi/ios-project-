package com.bucanante.cryptohouse.screens.models

data class FirestoreCoin (
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    val current_price: Double,
    val quantity: Double,
    val purchasePrice: Double
)