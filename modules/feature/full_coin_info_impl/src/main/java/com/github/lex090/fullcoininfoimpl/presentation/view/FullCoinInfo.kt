package com.github.lex090.fullcoininfoimpl.presentation.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.github.lex090.baseui.R
import com.github.lex090.coreapi.presentation.uiSate.BaseUiState
import com.github.lex090.coreapi.presentation.uiSate.UiStateEntity
import com.github.lex090.corediapi.AppDependenciesProvidersHolder
import com.github.lex090.fullcoininfoimpl.data.ScarletLifecycle
import com.github.lex090.fullcoininfoimpl.databinding.FullCoinInfoBsdfLayoutBinding
import com.github.lex090.fullcoininfoimpl.di.DaggerFullCoinInfoComponent
import com.github.lex090.fullcoininfoimpl.presentation.viewmodel.FullCoinInfoViewModel
import com.github.lex090.fullcoininfoimpl.presentation.viewmodel.entityUI.CoinInfoUiEntity
import com.github.lex090.fullcoininfoimpl.presentation.viewmodel.entityUI.DecreasePriceUiEntity
import com.github.lex090.fullcoininfoimpl.presentation.viewmodel.entityUI.FavoriteUiEntity
import com.github.lex090.fullcoininfoimpl.presentation.viewmodel.entityUI.IncreasePriceUiEntity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

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

    override fun onDestroyView() {
        scarletLifecycle.stop()
        viewModel.clear()
        _viewBinding = null
        super.onDestroyView()
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
        viewBinding.mainIdGroup.visibility = View.INVISIBLE
        viewBinding.shimmerLayout.isVisible = true
        viewBinding.shimmerLayout.startShimmer()
    }

    private fun processError(
        exception: Throwable,
        message: String? = null
    ) {
        showLoadingState()
        Snackbar.make(this.requireView(), message ?: "", Snackbar.LENGTH_SHORT).show()
        Log.i("myDebug", "processError: exception -> ${exception.message}")
    }

    private fun processSuccessStateData(uiStateEntity: UiStateEntity) {
        when (uiStateEntity) {
            is CoinInfoUiEntity -> {
                setAndShowFullCoinDataToScreen(uiStateEntity)
            }
            is IncreasePriceUiEntity -> {

            }
            is DecreasePriceUiEntity -> {

            }
            is FavoriteUiEntity -> {

            }
        }
    }

    private fun setAndShowFullCoinDataToScreen(coinInfo: CoinInfoUiEntity) {
        Log.i("myDebug", "setAndShowFullCoinDataToScreen: ${coinInfo}")
        with(viewBinding) {
            mainIdGroup.isVisible = true
            shimmerLayout.stopShimmer()
            shimmerLayout.isVisible = false

            coinInfoLayout.tvCoinRang.text = coinInfo.rang
            coinInfoLayout.tvCoinSymbol.text = coinInfo.symbol
            coinInfoLayout.tvCoinName.text = coinInfo.name
            coinInfoLayout.tvCoinPrice.text = coinInfo.price
            coinInfoLayout.tvCoinPricePercentage.text = coinInfo.priceChanging

            if (!coinInfo.imageUrl.isNullOrEmpty()) {
                loadCoinImageToIV(coinInfo.imageUrl)
            }
            setIsFavoriteIv(coinInfo)

            marketCapAmount.text = coinInfo.marketCap
            volume24hAmount.text = coinInfo.volume24H
            fullyDillMCapAmount.text = coinInfo.fullyDillMCap

            tvDescription.text = coinInfo.description
        }
    }

    private fun setIsFavoriteIv(coinInfo: CoinInfoUiEntity) {
        if (coinInfo.isFavorite) {
            viewBinding.coinInfoLayout.btnFavorite.background =
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_baseline_star_24
                )
        } else {
            viewBinding.coinInfoLayout.btnFavorite.background = ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_star_outline_24
            )
        }
    }

    private fun loadCoinImageToIV(imageUrl: String) {
        Picasso
            .get()
            .load(imageUrl)
            .placeholder(R.drawable.round_background_shimmer)
            .error(R.drawable.round_background_shimmer)
            .into(viewBinding.coinInfoLayout.ivCoin)
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