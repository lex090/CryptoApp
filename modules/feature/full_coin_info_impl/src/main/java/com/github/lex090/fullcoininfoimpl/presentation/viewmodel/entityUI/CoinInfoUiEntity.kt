package com.github.lex090.fullcoininfoimpl.presentation.viewmodel.entityUI

data class CoinInfoUiEntity(
    val rang: String,
    val imageUrl: String,
    val symbol: String,
    val name: String,
    val price: Double,
    val priceChanging: Double,
    val isFavorite: Boolean,
    val marketCap: Double,
    val volume24H: Double,
    val fullyDillMCap: Double,
    val description: String,
    val links: List<LinkUI>
)

data class LinkUI(
    val url: String,
    val host: String
)

//fun Coin.toCoinInfoUiEntity(): CoinInfoUiEntity =
//    CoinInfoUiEntity(
//        id = id,
//        name = this.name,
//        price = price,
//        isFavorite = isFavorite
//    )
//
//fun CoinInfoUiEntity.toCoin(isFavoriteNewValue: Boolean? = null): Coin = Coin(
//    id = id,
//    name = name,
//    price = price,
//    isFavorite = isFavoriteNewValue ?: isFavorite
//)