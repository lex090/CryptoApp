package com.github.lex090.fullcoininfoimpl.presentation.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.parseAsHtml
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.github.lex090.baseui.presentation.view.checkFavorite
import com.github.lex090.baseui.presentation.view.loadCoinImageToIV
import com.github.lex090.coreapi.presentation.uiSate.BaseUiState
import com.github.lex090.coreapi.presentation.uiSate.UiStateEntity
import com.github.lex090.corediapi.AppDependenciesProvidersHolder
import com.github.lex090.fullcoininfoimpl.data.ScarletLifecycle
import com.github.lex090.fullcoininfoimpl.databinding.FullCoinInfoBsdfLayoutBinding
import com.github.lex090.fullcoininfoimpl.di.DaggerFullCoinInfoComponent
import com.github.lex090.fullcoininfoimpl.presentation.viewmodel.FullCoinInfoViewModel
import com.github.lex090.fullcoininfoimpl.presentation.viewmodel.entityUI.CoinInfoUiEntity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.github.lex090.baseui.R as MainR

class FullCoinInfo : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: FullCoinInfoViewModel.Factory

    @Inject
    lateinit var scarletLifecycle: ScarletLifecycle

    private val screenArgs by navArgs<FullCoinInfoArgs>()

    private val viewModel by viewModels<FullCoinInfoViewModel> {
        viewModelFactory
    }

    private var _viewBinding: FullCoinInfoBsdfLayoutBinding? = null
    private val viewBinding: FullCoinInfoBsdfLayoutBinding
        get() = _viewBinding!!

    override fun onAttach(context: Context) {
        injectDependencies()
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FullCoinInfoBsdfLayoutBinding.inflate(
            inflater,
            container,
            false
        )
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupBottomSheetBehavior()
        subscribeToScreenInfo()
    }

    override fun onDestroyView() {
        scarletLifecycle.stop()
        viewModel.clear()
        _viewBinding = null
        super.onDestroyView()
    }

    private fun setupBottomSheetBehavior() {
        val behavior = (dialog as BottomSheetDialog).behavior
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.skipCollapsed = true
    }

    private fun subscribeToScreenInfo() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel
                    .screenState
                    .onStart {
                        scarletLifecycle.start()
                        viewModel.getCoinInfo(screenArgs.coinId)
                    }
                    .collect(::processState)
            }
        }
    }

    private fun processState(state: BaseUiState<UiStateEntity>) {
        when (state) {
            is BaseUiState.Error -> {
                processError(state.exception, state.message)
            }
            BaseUiState.Loading -> showLoadingState()
            is BaseUiState.Success -> {
                processSuccessStateData(state.data)
            }
        }
    }

    private fun showLoadingState() {
        viewBinding.contextMenuItemSnackBarHost.isVisible = false
        viewBinding.mainIdGroup.visibility = View.INVISIBLE
        viewBinding.shimmerLayout.isVisible = true
        viewBinding.shimmerLayout.startShimmer()
    }

    private fun processError(
        exception: Throwable,
        message: String? = null
    ) {
        showLoadingState()
        val errorMessage = message ?: requireContext().getString(MainR.string.defaultErrorText)
        showErrorSnackBar(message = errorMessage)
    }

    private fun showErrorSnackBar(message: String) {
        viewBinding.contextMenuItemSnackBarHost.isVisible = true
        val view = viewBinding.contextMenuItemSnackBarHost
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).apply {
            setAction(MainR.string.retryErrorButton) {
                subscribeToScreenInfo()
                dismiss()
            }
        }.show()
    }

    private fun processSuccessStateData(uiStateEntity: UiStateEntity) {
        when (uiStateEntity) {
            is CoinInfoUiEntity -> {
                setAndShowFullCoinDataToScreen(uiStateEntity)
            }
        }
    }

    private fun setAndShowFullCoinDataToScreen(coinInfo: CoinInfoUiEntity) {
        Log.i("myDebug", "setAndShowFullCoinDataToScreen: ${coinInfo}")
        with(viewBinding) {
            contextMenuItemSnackBarHost.isVisible = false
            mainIdGroup.isVisible = true
            shimmerLayout.stopShimmer()
            shimmerLayout.isVisible = false

            coinInfoLayout.tvCoinRang.text = coinInfo.rang
            coinInfoLayout.tvCoinSymbol.text = coinInfo.symbol
            coinInfoLayout.tvCoinName.text = coinInfo.name
            coinInfoLayout.tvCoinPrice.text = coinInfo.price
            coinInfoLayout.tvCoinPricePercentage.text = coinInfo.priceChanging

            viewBinding.coinInfoLayout.ivCoin.loadCoinImageToIV(coinInfo.imageUrl)
            setIsFavoriteIv(coinInfo)

            marketCapAmount.text = coinInfo.marketCap
            volume24hAmount.text = coinInfo.volume24H
            fullyDillMCapAmount.text = coinInfo.fullyDillMCap

            tvDescription.text = coinInfo.description.parseAsHtml()
        }
    }

    private fun setIsFavoriteIv(coinInfo: CoinInfoUiEntity) {
        with(viewBinding.coinInfoLayout) {
            btnFavorite.checkFavorite(coinInfo.isFavorite)
            btnFavorite.setOnFavoritesClickBehaviours(coinInfo.isFavorite)
        }
    }

    private fun AppCompatTextView.setOnFavoritesClickBehaviours(
        isFavorite: Boolean
    ) {
        if (isFavorite) {
            setOnClickListener {
                viewModel.clickOnRemoveCoinFromFavorites()
            }
        } else {
            setOnClickListener {
                viewModel.clickOnAddCoinToFavorites()
            }
        }
    }

    private fun injectDependencies() {
        val dependenciesProvider = this.activity?.application as? AppDependenciesProvidersHolder
        dependenciesProvider?.let {
            val component = DaggerFullCoinInfoComponent
                .factory()
                .create(
                    dependencies = it.getProvider()
                )
            component.inject(this)
        }
    }
}