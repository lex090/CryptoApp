@file:OptIn(ExperimentalCoroutinesApi::class)

package com.github.lex090.featurecoinslistfragmentimpl.presentation.viewmodel

import app.cash.turbine.test
import com.github.lex090.basecoins.domain.usecases.IGetCoinListFlowUseCase
import com.github.lex090.basefavoriteimpl.domain.usecases.IAddCoinToFavoritesUseCase
import com.github.lex090.basefavoriteimpl.domain.usecases.IRemoveCoinFromFavoritesUseCase
import com.github.lex090.baseui.presentation.viewmodel.entity.toCoinUiEntity
import com.github.lex090.baseui.presentation.viewmodel.entity.toCoinUiEntityList
import com.github.lex090.coreapi.ResultOf
import com.github.lex090.coreapi.presentation.uiSate.BaseUiState
import com.github.lex090.featurecoinslistfragmentimpl.presentation.CoroutinesTestRule
import com.github.lex090.featurecoinslistfragmentimpl.presentation.coin1
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class CoinListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = CoroutinesTestRule()

    private val getCoinListFlowUseCase: IGetCoinListFlowUseCase = mock()
    private val addCoinToFavoritesUseCase: IAddCoinToFavoritesUseCase =
        mock()
    private val removeCoinFromFavoritesUseCase: IRemoveCoinFromFavoritesUseCase =
        mock()

    @Test
    fun `add to favorite test`() = runTest {
        val original = coin1
        val expected = coin1.copy(isFavorite = true)

        val viewModel = CoinListViewModel(
            getCoinListFlowUseCase,
            addCoinToFavoritesUseCase,
            removeCoinFromFavoritesUseCase
        )

        viewModel.clickOnAddCoinToFavorites(original)

        verify(addCoinToFavoritesUseCase, times(1))
            .execute(expected)
    }

    @Test
    fun `remove from favorite test`() = runTest {
        val original = coin1
        val expected = coin1.copy(isFavorite = false)

        val viewModel = CoinListViewModel(
            getCoinListFlowUseCase,
            addCoinToFavoritesUseCase,
            removeCoinFromFavoritesUseCase
        )

        viewModel.clickOnRemoveCoinFromFavorites(original)


        verify(removeCoinFromFavoritesUseCase, times(1))
            .execute(expected)
    }


    @Test
    fun `process success result of`() = runTest {
        val original = ResultOf.Success(listOf(coin1))
        val expected = BaseUiState.Success(
            original.data.map { it.toCoinUiEntity() }.toCoinUiEntityList()
        )

        val flow = flow {
            emit(original)
        }

        val viewModel = CoinListViewModel(
            getCoinListFlowUseCase,
            addCoinToFavoritesUseCase,
            removeCoinFromFavoritesUseCase
        )

        whenever(getCoinListFlowUseCase.execute()).thenReturn(flow)

        viewModel.subscribeToState()

        viewModel.screenState.test {
            assertEquals(expected, awaitItem())
        }
    }

    @Test
    fun `process error result of`() = runTest {
        val exception = RuntimeException("Error message")
        val original = ResultOf.Error(exception, exception.message)
        val expected = BaseUiState.Error(exception, exception.message)

        val flow = flow {
            emit(original)
        }

        val viewModel = CoinListViewModel(
            getCoinListFlowUseCase,
            addCoinToFavoritesUseCase,
            removeCoinFromFavoritesUseCase
        )

        whenever(getCoinListFlowUseCase.execute()).thenReturn(flow)

        viewModel.subscribeToState()

        viewModel.screenState.test {
            assertEquals(expected, awaitItem())
        }
    }
}
