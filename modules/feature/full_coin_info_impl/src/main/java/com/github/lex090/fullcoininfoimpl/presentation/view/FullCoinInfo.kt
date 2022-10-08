package com.github.lex090.fullcoininfoimpl.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.github.lex090.corediapi.AppDependenciesProvidersHolder
import com.github.lex090.fullcoininfoimpl.databinding.FullCoinInfoBsdfLayoutBinding
import com.github.lex090.fullcoininfoimpl.di.DaggerFullCoinInfoComponent
import com.github.lex090.fullcoininfoimpl.presentation.view.entity.CoinInfoUiEntity
import com.github.lex090.fullcoininfoimpl.presentation.view.entity.toCoinInfoUiEntity
import com.github.lex090.fullcoininfoimpl.data.ScarletLifecycle
import com.github.lex090.fullcoininfoimpl.presentation.viewmodel.FullCoinInfoViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
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
                        result?.toCoinInfoUiEntity()
                    }
                    .collect { result ->
                        processCoinInfo(coinInfo = result)
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

    private fun readyCoinInfoState(coinInfo: CoinInfoUiEntity) {
//        viewBinding.text1.text = coinInfo.name
//        viewBinding.text2.text = coinInfo.price.toString()
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