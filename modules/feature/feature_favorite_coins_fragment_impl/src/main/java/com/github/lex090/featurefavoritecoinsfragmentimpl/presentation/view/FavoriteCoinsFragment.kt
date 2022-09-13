package com.github.lex090.featurefavoritecoinsfragmentimpl.presentation.view

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
import com.github.lex090.corediapi.AppDependenciesProvidersHolder
import com.github.lex090.featurefavoritecoinsfragmentimpl.databinding.FragmentFavoriteCoinsBinding
import com.github.lex090.featurefavoritecoinsfragmentimpl.di.DaggerFavoriteCoinsFragmentComponent
import com.github.lex090.featurefavoritecoinsfragmentimpl.presentation.viewmodel.FavoriteCoinsViewModel
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


class FavoriteCoinsFragment : Fragment() {

    private var _viewBinding: FragmentFavoriteCoinsBinding? = null
    private val viewBinding: FragmentFavoriteCoinsBinding
        get() = _viewBinding!!

    @Inject
    lateinit var viewModelFactory: FavoriteCoinsViewModel.Factory

    private val viewModel by viewModels<FavoriteCoinsViewModel> {
        viewModelFactory
    }

    @Inject
    lateinit var coinListItemAdapterFactory: ICoinListItemAdapterFactory

    @Inject
    lateinit var coinListDiffAdapterFactory: CoinListDiffAdapter.Factory

    private val adapterDelegatesManager by lazy {
        AdapterDelegatesManager<List<DisplayableItem>>().apply {
            addDelegate(
                coinListItemAdapterFactory
                    .createCommonCoinListItemAdapterFactory(
                        { _, _ -> },
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
        _viewBinding = FragmentFavoriteCoinsBinding.inflate(inflater, container, false)
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

    private fun initRecyclerView() {
        viewBinding.rvFavoritesList.adapter = adapter
        viewBinding.rvFavoritesList.layoutManager = LinearLayoutManager(context)
    }

    private fun initDataSubscriptions() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel
                    .favoriteCoinsList
                    .map { result ->
                        result.mapIndexed { index, value ->
                            value.toCoinUiEntity(index + 1)
                        }
                    }
                    .collect(::processCoinsList)
            }
        }
    }

    private fun processCoinsList(result: List<CoinUiEntity>) {
        adapter.items = result
    }

    private fun clickOnRemoveCoinFromFavorites(position: Int, coinUiEntity: CoinUiEntity) {
        viewModel.clickOnRemoveCoinFromFavorites(
            position = position, coin = coinUiEntity.toCoin()
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