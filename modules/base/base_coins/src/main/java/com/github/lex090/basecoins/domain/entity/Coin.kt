package com.github.lex090.basecoins.domain.entity

data class Coin(
    val id: String,
    val rang: String? = null,
    val imageUrl: String? = null,
    val symbol: String? = null,
    val name: String,
    val price: Double,
    val priceChanging: Double? = null,
    val isFavorite: Boolean,
    val marketCap: Double? = null,
    val volume24h: Double? = null,
    val fullyDilutedValuation: Double? = null,
    val description: String? = null,
)