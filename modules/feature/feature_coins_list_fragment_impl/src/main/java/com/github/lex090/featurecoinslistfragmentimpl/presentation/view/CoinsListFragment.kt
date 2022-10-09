package com.github.lex090.featurecoinslistfragmentimpl.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.lex090.baseui.presentation.view.adapters.ICoinListItemAdapterFactory
import com.github.lex090.baseui.presentation.view.diffutil.CoinListDiffAdapter
import com.github.lex090.baseui.presentation.viewmodel.entity.CoinUiEntity
import com.github.lex090.baseui.presentation.viewmodel.entity.CoinUiEntityList
import com.github.lex090.baseui.presentation.viewmodel.entity.DisplayableItem
import com.github.lex090.baseui.presentation.viewmodel.entity.toCoin
import com.github.lex090.coreapi.presentation.uiSate.BaseUiState
import com.github.lex090.coreapi.presentation.uiSate.UiStateEntity
import com.github.lex090.corediapi.AppDependenciesProvidersHolder
import com.github.lex090.featurecoinslistfragmentimpl.databinding.FragmentCoinsListBinding
import com.github.lex090.featurecoinslistfragmentimpl.di.DaggerCoinListFragmentComponent
import com.github.lex090.featurecoinslistfragmentimpl.presentation.viewmodel.CoinListViewModel
import com.google.android.material.snackbar.Snackbar
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.github.lex090.baseui.R as MainR


class CoinsListFragment : Fragment() {

    private var _viewBinding: FragmentCoinsListBinding? = null
    private val viewBinding: FragmentCoinsListBinding
        get() = _viewBinding!!

    @Inject
    lateinit var viewModelFactory: CoinListViewModel.Factory

    @Inject
    lateinit var coinListItemAdapterFactory: ICoinListItemAdapterFactory

    @Inject
    lateinit var coinListDiffAdapterFactory: CoinListDiffAdapter.Factory

    private val viewModel by viewModels<CoinListViewModel> {
        viewModelFactory
    }

    private val adapterDelegatesManager by lazy {
        AdapterDelegatesManager<List<DisplayableItem>>().apply {
            addDelegate(
                coinListItemAdapterFactory
                    .createCommonCoinListItemAdapterFactory(
                        this@CoinsListFragment::onCoinItemClick,
                        this@CoinsListFragment::clickOnAddCoinToFavorites,
                        this@CoinsListFragment::clickOnRemoveCoinFromFavorites,
                    )
            )
        }
    }

    private val adapter by lazy {
        coinListDiffAdapterFactory.create(adapterDelegatesManager)
    }

    override fun onAttach(context: Context) {
        injectDependencies()
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentCoinsListBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initDataSubscriptions()
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }

    private fun processState(state: BaseUiState<UiStateEntity>) {
        when (state) {
            is BaseUiState.Error -> {
                val errorMessage =
                    state.message ?: requireContext().getString(MainR.string.defaultErrorText)
                processErrorScreenState(errorMessage)
            }
            BaseUiState.Loading -> showLoadingScreenState()
            is BaseUiState.Success -> {
                processSuccessScreenState(state.data)
            }
        }
    }

    private fun processSuccessScreenState(data: UiStateEntity) {
        when (data) {
            is CoinUiEntityList -> {
                adapter.setItems(data.items) {
                    showSuccessSate()
                }
            }
        }
    }

    private fun showLoadingScreenState() {
        viewBinding.contextMenuItemSnackBarHost.isVisible = false
        viewBinding.rvCoinsList.isVisible = false
        viewBinding.shimmerLayout.root.isVisible = true
        viewBinding.shimmerLayout.root.startShimmer()
    }

    private fun showSuccessSate() {
        viewBinding.contextMenuItemSnackBarHost.isVisible = false
        viewBinding.rvCoinsList.isVisible = true
        viewBinding.shimmerLayout.root.stopShimmer()
        viewBinding.shimmerLayout.root.isVisible = false
    }

    private fun processErrorScreenState(
        message: String
    ) {
        viewBinding.contextMenuItemSnackBarHost.isVisible = true
        val view = viewBinding.contextMenuItemSnackBarHost
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).apply {
            setAction(MainR.string.retryErrorButton) {
                initDataSubscriptions()
                dismiss()
            }
        }.show()
    }

    private fun initDataSubscriptions() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel
                    .screenState
                    .onStart {
                        viewModel.onViewsInit()
                    }
                    .collect(::processState)
            }
        }
    }

    private fun initRecyclerView() {
        viewBinding.rvCoinsList.adapter = adapter
        viewBinding.rvCoinsList.layoutManager = LinearLayoutManager(context)
    }

    private fun clickOnAddCoinToFavorites(position: Int, coinUiEntity: CoinUiEntity) {
        viewModel.clickOnAddCoinToFavorites(
            position = position, coin = coinUiEntity.toCoin(isFavoriteNewValue = true)
        )
    }

    private fun clickOnRemoveCoinFromFavorites(position: Int, coinUiEntity: CoinUiEntity) {
        viewModel.clickOnRemoveCoinFromFavorites(
            position = position, coin = coinUiEntity.toCoin(isFavoriteNewValue = false)
        )
    }

    private fun onCoinItemClick(coinUiEntity: CoinUiEntity) {
        findNavController()
            .navigate(
                CoinsListFragmentDirections
                    .actionCoinsListFragmentToNavGraphFullCoinInfo(
                        coinUiEntity.originalData.id
                    )
            )
    }

    private fun injectDependencies() {
        val dependenciesProvider = this.activity?.application as? AppDependenciesProvidersHolder
        dependenciesProvider?.let {
            val component = DaggerCoinListFragmentComponent
                .factory()
                .create(
                    dependencies = it.getProvider()
                )
            component.inject(this)
        }
    }
}