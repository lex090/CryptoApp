package com.github.lex090.fullcoininfoimpl.presentation.view

import android.content.Context
import android.os.Bundle
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
import com.github.lex090.corediapi.AppDependenciesProvidersHolder
import com.github.lex090.fullcoininfoimpl.data.ScarletLifecycle
import com.github.lex090.fullcoininfoimpl.databinding.FullCoinInfoBsdfLayoutBinding
import com.github.lex090.fullcoininfoimpl.di.DaggerFullCoinInfoComponent
import com.github.lex090.fullcoininfoimpl.presentation.viewmodel.FullCoinInfoViewModel
import com.github.lex090.fullcoininfoimpl.presentation.viewmodel.entityUI.CoinInfoUiEntity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
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
        scarletLifecycle.start()
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel
                    .coinInfo
                    .map { result ->
//                        result?.toCoinInfoUiEntity()
                    }
                    .collect { result ->
//                        processCoinInfo(coinInfo = result)
                    }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getCoinInfo(screenArgs.coinId)
    }

    override fun onDestroyView() {
        scarletLifecycle.stop()
        viewModel.clear()
        _viewBinding = null
        super.onDestroyView()
    }

    private fun processCoinInfo(coinInfo: CoinInfoUiEntity?) {
//        if (coinInfo == null) {
        showLoadingState()
        lifecycleScope.launch {
            delay(3000)
            viewBinding.shimmerLayout.isVisible = false
            viewBinding.mainIdGroup.visibility = View.VISIBLE
            viewBinding.shimmerLayout.stopShimmer()
        }
    }

    private fun showLoadingState() {
        viewBinding.mainIdGroup.visibility = View.INVISIBLE
        viewBinding.shimmerLayout.isVisible = true
        viewBinding.shimmerLayout.startShimmer()
    }

    private fun setAndShowFullCoinDataToScreen(coinInfo: CoinInfoUiEntity) {
        viewBinding.mainIdGroup.isVisible = true
        viewBinding.shimmerLayout.stopShimmer()
        viewBinding.shimmerLayout.isVisible = false

        viewBinding.coinInfoLayout.tvCoinSymbol.text = coinInfo.symbol
        viewBinding.coinInfoLayout.tvCoinName.text = coinInfo.name
        viewBinding.coinInfoLayout.tvCoinRang.text = coinInfo.rang
        viewBinding.coinInfoLayout.tvCoinPrice.text = "${coinInfo.price}$"
        viewBinding.coinInfoLayout.tvCoinPricePercentage.visibility = View.INVISIBLE
        loadCoinImageToIV(coinInfo)
        setIsFavoriteIv(coinInfo)

        viewBinding.marketCapAmount.text = "${coinInfo.marketCap / 1000000} B"
        viewBinding.volume24hAmount.text = "${coinInfo.volume24H / 1000000} B"
        viewBinding.fullyDillMCapAmount.text = "${coinInfo.fullyDillMCap / 1000000} B"

        viewBinding.tvDescription.text = coinInfo.description
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

    private fun loadCoinImageToIV(coinInfo: CoinInfoUiEntity) {
        Picasso
            .get()
            .load(coinInfo.imageUrl)
            .placeholder(com.github.lex090.baseui.R.drawable.round_background_shimmer)
            .error(com.github.lex090.baseui.R.drawable.round_background_shimmer)
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