package com.github.lex090.featurecoinslistfragmentimpl.presentation.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.lex090.corediapi.AppDependenciesProvidersHolder
import com.github.lex090.corenetworkapi.ResultOf
import com.github.lex090.featurecoinslistfragmentimpl.databinding.FragmentCoinsListBinding
import com.github.lex090.featurecoinslistfragmentimpl.di.DaggerCoinListFragmentComponent
import com.github.lex090.featurecoinslistfragmentimpl.presentation.view.adapters.ICoinListItemAdapterFactory
import com.github.lex090.featurecoinslistfragmentimpl.presentation.view.diffutil.CoinListDiffAdapter
import com.github.lex090.featurecoinslistfragmentimpl.presentation.viewmodel.CoinListViewModel
import com.google.android.material.snackbar.Snackbar
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
                    .createCommonCoinListItemAdapterFactory(this@CoinsListFragment::onListItemClick)
            )
        }
    }

    private val adapter by lazy {
        coinListDiffAdapterFactory.create(adapterDelegatesManager)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectDependencies()
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
        super.onDestroyView()
        _viewBinding = null
    }

    private fun processCoinsList(result: ResultOf<CoinsListUiEntity>) {
        when (result) {
            is ResultOf.Success -> viewCoinsList(result.data)
            is ResultOf.Error -> showError(result)
        }
    }

    private fun viewCoinsList(data: CoinsListUiEntity) {
        adapter.items = data.coinsList
    }

    private fun showError(error: ResultOf.Error) {

    }

    private fun initDataSubscriptions() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel
                    .coinsList
                    .map { result ->
                        ResultOf.Success(data = result.data.toCoinsListUiEntity())
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

    private fun onListItemClick(position: Int, coinUiEntity: CoinUiEntity, isFavorite: Boolean) {
        val items = mutableListOf<DisplayableItem>()
        items.addAll(adapter.items)
        items[position] = coinUiEntity.copy(isFavorite = isFavorite)
        Log.i("myDebug", "onListItemClick: items -> $items")
        adapter.items = items
        Snackbar.make(viewBinding.root, coinUiEntity.name, Snackbar.LENGTH_SHORT).show()
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