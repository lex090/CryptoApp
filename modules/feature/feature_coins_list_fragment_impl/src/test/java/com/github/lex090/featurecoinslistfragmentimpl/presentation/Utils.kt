package com.github.lex090.featurecoinslistfragmentimpl.presentation

import com.github.lex090.basecoins.domain.entity.Coin
import com.github.lex090.baseui.presentation.viewmodel.entity.CoinUiEntity
import com.github.lex090.baseui.presentation.viewmodel.entity.CoinUiEntityList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

val coin2 = Coin(
    id = "122",
    rang = "12",
    imageUrl = "https://test2.com",
    symbol = "BTC2",
    name = "bitcoin2",
    price = 20000.2,
    priceChanging = 0.04,
    isFavorite = true,
    marketCap = null,
    volume24h = null,
    fullyDilutedValuation = null,
    description = null
)

val coin1 = Coin(
    id = "12",
    rang = "1",
    imageUrl = "https://test.com",
    symbol = "BTC",
    name = "bitcoin",
    price = 20000.0,
    priceChanging = 0.02,
    isFavorite = true,
    marketCap = null,
    volume24h = null,
    fullyDilutedValuation = null,
    description = null
)

val testCoins = listOf(coin1, coin2)

val coinUiEntity1 = CoinUiEntity(
    rang = "1",
    symbol = "BTC",
    name = "bitcoin",
    price = "20000.0",
    priceChanging = "0.02",
    isFavorite = true,
    imageUrl = "https://test.com",
    originalData = coin1
)

val coinUiEntityList1 = CoinUiEntityList(
    items = listOf(coinUiEntity1)
)

@ExperimentalCoroutinesApi
class CoroutinesTestRule(
    private val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}
