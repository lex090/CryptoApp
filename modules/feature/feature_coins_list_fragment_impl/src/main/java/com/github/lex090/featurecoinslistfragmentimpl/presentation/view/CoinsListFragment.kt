package com.github.lex090.featurecoinslistfragmentimpl.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.lex090.baseui.presentation.view.adapters.ICoinListItemAdapterFactory
import com.github.lex090.baseui.presentation.view.diffutil.CoinListDiffAdapter
import com.github.lex090.baseui.presentation.view.entity.CoinUiEntity
import com.github.lex090.baseui.presentation.view.entity.DisplayableItem
import com.github.lex090.baseui.presentation.view.entity.toCoin
import com.github.lex090.baseui.presentation.view.entity.toCoinUiEntity
import com.github.lex090.coreapi.ResultOf
import com.github.lex090.corediapi.AppDependenciesProvidersHolder
import com.github.lex090.featurecoinslistfragmentimpl.databinding.FragmentCoinsListBinding
import com.github.lex090.featurecoinslistfragmentimpl.di.DaggerCoinListFragmentComponent
import com.github.lex090.featurecoinslistfragmentimpl.presentation.viewmodel.CoinListViewModel
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

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

    override fun onResume() {
        super.onResume()
        viewModel.onViewsInit()
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }

    private fun processCoinsList(result: List<CoinUiEntity>) {
        adapter.items = result
    }

    private fun showError(error: ResultOf.Error) {

    }

    private fun initDataSubscriptions() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel
                    .coinsList
                    .map { result ->
                        result.mapIndexed { index, value ->
                            value.toCoinUiEntity(index + 1)
                        }
                    }
                    .collect { result ->
                        processCoinsList(result = result)
                    }
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