package com.github.lex090.featurefavoritecoinsfragmentimpl.presentation.view

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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.lex090.baseui.R
import com.github.lex090.baseui.presentation.view.adapters.ICoinListItemAdapterFactory
import com.github.lex090.baseui.presentation.view.diffutil.CoinListDiffAdapter
import com.github.lex090.baseui.presentation.viewmodel.entity.CoinUiEntity
import com.github.lex090.baseui.presentation.viewmodel.entity.CoinUiEntityList
import com.github.lex090.baseui.presentation.viewmodel.entity.DisplayableItem
import com.github.lex090.coreapi.presentation.uiSate.BaseUiState
import com.github.lex090.coreapi.presentation.uiSate.UiStateEntity
import com.github.lex090.corediapi.AppDependenciesProvidersHolder
import com.github.lex090.featurefavoritecoinsfragmentimpl.databinding.FragmentFavoriteCoinsBinding
import com.github.lex090.featurefavoritecoinsfragmentimpl.di.DaggerFavoriteCoinsFragmentComponent
import com.github.lex090.featurefavoritecoinsfragmentimpl.presentation.viewmodel.FavoriteCoinsViewModel
import com.google.android.material.snackbar.Snackbar
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressWarnings("TooManyFunctions")
class FavoriteCoinsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: FavoriteCoinsViewModel.Factory

    @Inject
    lateinit var coinListItemAdapterFactory: ICoinListItemAdapterFactory

    @Inject
    lateinit var coinListDiffAdapterFactory: CoinListDiffAdapter.Factory

    private var _viewBinding: FragmentFavoriteCoinsBinding? = null
    private val viewBinding: FragmentFavoriteCoinsBinding
        get() = _viewBinding!!

    private val viewModel by viewModels<FavoriteCoinsViewModel> {
        viewModelFactory
    }

    private val adapterDelegatesManager by lazy {
        AdapterDelegatesManager<List<DisplayableItem>>().apply {
            addDelegate(
                coinListItemAdapterFactory
                    .createCommonCoinListItemAdapterFactory(
                        this@FavoriteCoinsFragment::onCoinItemClick,
                        {},
                        this@FavoriteCoinsFragment::clickOnRemoveCoinFromFavorites,
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
        _viewBinding =
            FragmentFavoriteCoinsBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        subscribeToScreenState()
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }

    private fun initRecyclerView() {
        with(viewBinding) {
            rvFavoritesList.adapter = adapter
            rvFavoritesList.layoutManager = LinearLayoutManager(context)
            val decorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            rvFavoritesList.addItemDecoration(decorator)
        }
    }

    private fun subscribeToScreenState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel
                    .screenState
                    .collect(::processState)
            }
        }
    }

    private fun processState(state: BaseUiState<UiStateEntity>) {
        when (state) {
            is BaseUiState.Error -> {
                val errorMessage =
                    state.message ?: requireContext().getString(R.string.defaultErrorText)
                processErrorScreenState(errorMessage)
            }
            BaseUiState.Loading -> showLoadingScreenState()
            is BaseUiState.Success -> {
                processSuccessScreenState(state.data)
            }
        }
    }

    private fun processErrorScreenState(
        message: String
    ) {
        viewBinding.contextMenuItemSnackBarHost.isVisible = true
        val view = viewBinding.contextMenuItemSnackBarHost
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).apply {
            setAction(R.string.retryErrorButton) {
                subscribeToScreenState()
                dismiss()
            }
        }.show()
    }

    private fun showLoadingScreenState() {
        viewBinding.contextMenuItemSnackBarHost.isVisible = false
        viewBinding.rvFavoritesList.isVisible = false
        viewBinding.shimmerLayout.root.isVisible = true
        viewBinding.shimmerLayout.root.startShimmer()
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

    private fun showSuccessSate() {
        viewBinding.contextMenuItemSnackBarHost.isVisible = false
        viewBinding.rvFavoritesList.isVisible = true
        viewBinding.shimmerLayout.root.stopShimmer()
        viewBinding.shimmerLayout.root.isVisible = false
    }

    private fun clickOnRemoveCoinFromFavorites(coinUiEntity: CoinUiEntity) {
        viewModel.clickOnRemoveCoinFromFavorites(coin = coinUiEntity.originalData)
    }

    private fun onCoinItemClick(coinUiEntity: CoinUiEntity) {
        findNavController()
            .navigate(
                FavoriteCoinsFragmentDirections
                    .actionCoinsListFragmentToNavGraphFullCoinInfo(
                        coinUiEntity.originalData.id
                    )
            )
    }

    private fun injectDependencies() {
        val dependenciesProvider = this.activity?.application as? AppDependenciesProvidersHolder
        dependenciesProvider?.let {
            val component = DaggerFavoriteCoinsFragmentComponent
                .factory()
                .create(
                    dependencies = it.getProvider()
                )
            component.inject(this)
        }
    }
}