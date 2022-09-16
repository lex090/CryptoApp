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
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingDataAdapter
import androidx.paging.cachedIn
import androidx.paging.map
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
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

    @Inject
    lateinit var diffUtil: DiffUtil.ItemCallback<DisplayableItem>

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
        PagedDelegationAdapter(adapterDelegatesManager, diffUtil)
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

    private fun showError(error: ResultOf.Error) {

    }

    private var index = 0

    private fun initDataSubscriptions() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel
                    .coinsList
                    .map { result ->
                        result.map {
                            it.toCoinUiEntity(index++) as DisplayableItem
                        }
                    }
                    .onEach {
                        Log.i("myDebug", "onEach: $it")
                    }
                    .collect {
                        adapter.submitData(it)
                    }
            }
        }
    }

    private fun initRecyclerView() {
        viewBinding.rvCoinsList.adapter = adapter
        viewBinding.rvCoinsList.layoutManager = LinearLayoutManager(context)
        adapter.addOnPagesUpdatedListener {
            Log.i("myDebug", "addOnPagesUpdatedListener")
        }

        adapter.addLoadStateListener {
            Log.i("myDebug", "addLoadStateListener it -> $it")
        }
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

    class PagedDelegationAdapter<T : Any>(
        private var delegatesManager: AdapterDelegatesManager<List<T>>,
        diffCallback: DiffUtil.ItemCallback<T>
    ) : PagingDataAdapter<T, RecyclerView.ViewHolder>(diffCallback) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return delegatesManager.onCreateViewHolder(parent, viewType)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            getItem(position) // Internally triggers loading items around items around the given position
            delegatesManager.onBindViewHolder(snapshot().items, position, holder, null)
        }

        override fun onBindViewHolder(
            holder: RecyclerView.ViewHolder, position: Int,
            payloads: List<*>
        ) {
            getItem(position) // Internally triggers loading items around items around the given position
            delegatesManager.onBindViewHolder(snapshot().items, position, holder, payloads)
        }

        override fun getItemViewType(position: Int): Int {
            return delegatesManager.getItemViewType(snapshot().items, position)
        }

        override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
            delegatesManager.onViewRecycled(holder)
        }

        override fun onFailedToRecycleView(holder: RecyclerView.ViewHolder): Boolean {
            return delegatesManager.onFailedToRecycleView(holder)
        }

        override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
            delegatesManager.onViewAttachedToWindow(holder)
        }

        override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
            delegatesManager.onViewDetachedFromWindow(holder)
        }
    }
}