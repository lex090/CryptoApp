package com.github.lex090.basecoins.data

import com.github.lex090.basecoins.data.responses.CoinResponse
import com.github.lex090.basecoins.data.responses.FullCoinInfoResponse
import com.github.lex090.basecoins.domain.entity.Coin

fun List<CoinResponse>.mapData(
    isFavoritePredicate: (id: String) -> Boolean
): List<Coin> =
    map { coinResponse ->
        coinResponse.toCoin(isFavoritePredicate)
    }

fun CoinResponse.toCoin(
    isFavoritePredicate: (id: String) -> Boolean
): Coin = Coin(
    id = this.id,
    rang = marketCapRank.toString(),
    symbol = this.symbol,
    name = this.name,
    price = this.currentPrice,
    priceChanging = priceChangePercentage24H,
    isFavorite = isFavoritePredicate.invoke(this.id),
    marketCap = marketCap,
    volume24h = null,
    fullyDilutedValuation = null,
    description = null,
    imageUrl = image
)

fun FullCoinInfoResponse.toCoin(isFavorite: Boolean): Coin =
    Coin(
        id = id,
        rang = marketCapRank?.toString(),
        imageUrl = image?.large,
        symbol = symbol,
        name = name,
        price = marketData.price.usd,
        priceChanging = null,
        isFavorite = isFavorite,
        marketCap = marketData.marketCap?.usd,
        volume24h = marketData.totalVolume?.usd,
        fullyDilutedValuation = marketData.fullyDilutedValuation?.usd,
        description = description?.en
    )