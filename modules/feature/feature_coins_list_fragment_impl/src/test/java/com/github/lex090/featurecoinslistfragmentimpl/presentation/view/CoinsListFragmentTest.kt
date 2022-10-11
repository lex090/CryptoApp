package com.github.lex090.featurecoinslistfragmentimpl.presentation.view

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.lex090.baseui.presentation.view.adapters.ICoinListItemAdapterFactory
import com.github.lex090.baseui.presentation.view.diffutil.CoinListDiffAdapter
import com.github.lex090.baseui.presentation.viewmodel.entity.DisplayableItem
import com.github.lex090.coreapi.presentation.uiSate.BaseUiState
import com.github.lex090.coreapi.presentation.uiSate.UiStateEntity
import com.github.lex090.featurecoinslistfragmentimpl.R
import com.github.lex090.featurecoinslistfragmentimpl.presentation.viewmodel.CoinListViewModel
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(
    instrumentedPackages = ["androidx.loader.content"]
)
class CoinsListFragmentTest {

    private val coinListViewModel: CoinListViewModel = mock()
    private val coinListDiffAdapterFactory: CoinListDiffAdapter.Factory = mock()
    private val coinListDiffAdapter: CoinListDiffAdapter = mock()
    private val coinListItemAdapterFactory: ICoinListItemAdapterFactory = mock()
    private val adapterDelegate: AdapterDelegate<List<DisplayableItem>> = mock()


    @Test
    fun `test error screen State`() {

        val exception = RuntimeException("Error message")
        val errorState = BaseUiState.Error(exception)
        val flow = MutableStateFlow<BaseUiState<UiStateEntity>>(errorState)

        whenever(coinListViewModel.screenState).thenReturn(flow)

        val factoryFactoryMock = mock<CoinListViewModel.Factory>()

        whenever(factoryFactoryMock.create<CoinListViewModel>(any()))
            .thenReturn(coinListViewModel)

        whenever(
            coinListItemAdapterFactory
                .createCommonCoinListItemAdapterFactory(any(), any(), any())
        )
            .thenReturn(adapterDelegate)

        whenever(
            coinListDiffAdapterFactory
                .create(any())
        ).thenReturn(coinListDiffAdapter)

        val fragmentScenario =
            launchFragmentInContainer<CoinsListFragment>(
                initialState = Lifecycle.State.CREATED
            )

        fragmentScenario.onFragment { fragment ->
            fragment.coinListItemAdapterFactory = coinListItemAdapterFactory
            fragment.coinListDiffAdapterFactory = coinListDiffAdapterFactory
            fragment.viewModelFactory = factoryFactoryMock
            fragmentScenario.moveToState(Lifecycle.State.STARTED)
        }

        Espresso.onView(withId(R.id.contextMenuItemSnackBarHost))
            .check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

    @Test
    fun `test loading screen State`() {

        val loadingState = BaseUiState.Loading
        val flow = MutableStateFlow<BaseUiState<UiStateEntity>>(loadingState)

        whenever(coinListViewModel.screenState).thenReturn(flow)

        val factoryFactoryMock = mock<CoinListViewModel.Factory>()

        whenever(factoryFactoryMock.create<CoinListViewModel>(any()))
            .thenReturn(coinListViewModel)

        whenever(
            coinListItemAdapterFactory
                .createCommonCoinListItemAdapterFactory(any(), any(), any())
        )
            .thenReturn(adapterDelegate)

        whenever(
            coinListDiffAdapterFactory
                .create(any())
        ).thenReturn(coinListDiffAdapter)

        val fragmentScenario =
            launchFragmentInContainer<CoinsListFragment>(
                initialState = Lifecycle.State.CREATED
            )

        fragmentScenario.onFragment { fragment ->
            fragment.coinListItemAdapterFactory = coinListItemAdapterFactory
            fragment.coinListDiffAdapterFactory = coinListDiffAdapterFactory
            fragment.viewModelFactory = factoryFactoryMock
            fragmentScenario.moveToState(Lifecycle.State.STARTED)
        }

        Espresso.onView(withId(R.id.shimmerLayout))
            .check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }
}
