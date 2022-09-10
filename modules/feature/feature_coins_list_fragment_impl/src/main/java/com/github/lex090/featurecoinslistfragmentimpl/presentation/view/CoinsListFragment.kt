package com.github.lex090.featurecoinslistfragmentimpl.presentation.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.lex090.corediapi.AppDependenciesProvidersHolder
import com.github.lex090.corenetworkapi.ResultOf
import com.github.lex090.featurecoinslistfragmentimpl.R
import com.github.lex090.featurecoinslistfragmentimpl.databinding.FragmentCoinsListBinding
import com.github.lex090.featurecoinslistfragmentimpl.databinding.ItemSmallCoinInfoBinding
import com.github.lex090.featurecoinslistfragmentimpl.di.DaggerCoinListFragmentComponent
import com.github.lex090.featurecoinslistfragmentimpl.presentation.viewmodel.CoinListViewModel
import com.google.android.material.snackbar.Snackbar
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class CoinsListFragment : Fragment() {

    private var _viewBinding: FragmentCoinsListBinding? = null
    private val viewBinding: FragmentCoinsListBinding
        get() = _viewBinding!!

    @Inject
    lateinit var viewModelFactory: CoinListViewModel.Factory

    private val viewModel by viewModels<CoinListViewModel> {
        viewModelFactory
    }

    private fun cat2AdapterDelegate(itemClickedListener: (Int, CoinUiEntity, Boolean) -> Unit) =
        adapterDelegateViewBinding<CoinUiEntity, DisplayableItem, ItemSmallCoinInfoBinding>(
            { layoutInflater, root ->
                ItemSmallCoinInfoBinding.inflate(
                    layoutInflater,
                    root,
                    false
                )
            }
        ) {
            binding.btnFavorite.setOnClickListener {
                val isFavorite = !item.isFavorite
                itemClickedListener(this.adapterPosition, item, isFavorite)
            }

            bind {
                if (it.isNotEmpty()) {
                    Log.i("myDebug", "cat2AdapterDelegate: isNotEmpty()")
                    val item = it.first() as Boolean
                    if (item) {
                        binding.btnFavorite.background =
                            ContextCompat.getDrawable(context, R.drawable.ic_baseline_favorite_24)
                    } else {
                        binding.btnFavorite.background = ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_baseline_favorite_border_24
                        )
                    }
                } else {
                    Log.i("myDebug", "cat2AdapterDelegate: isNotEmpty() false")

                    binding.tvCoinName.text = item.name
                    binding.tvPositionId.text = item.position.toString()
                    binding.tvCoinPrice.text = item.price.toString()
                    if (item.isFavorite) {
                        binding.btnFavorite.background =
                            ContextCompat.getDrawable(context, R.drawable.ic_baseline_favorite_24)
                    } else {
                        binding.btnFavorite.background = ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_baseline_favorite_border_24
                        )
                    }
                }
            }
        }

    private val adapterDelegatesManager = AdapterDelegatesManager<List<DisplayableItem>>().apply {
        addDelegate(cat2AdapterDelegate(this@CoinsListFragment::onListItemClick))
    }

    class MyDiffUtilCallBack : DiffUtil.ItemCallback<DisplayableItem>() {
        override fun areItemsTheSame(oldItem: DisplayableItem, newItem: DisplayableItem): Boolean {
            Log.i("myDebug", "areItemsTheSame: oldItem -> $oldItem, newItem -> $newItem")
            return when {
                oldItem is CoinUiEntity && newItem is CoinUiEntity -> {
                    oldItem.name == newItem.name
                }
                else -> false
            }
        }

        override fun areContentsTheSame(
            oldItem: DisplayableItem,
            newItem: DisplayableItem
        ): Boolean {
            Log.i("myDebug", "areContentsTheSame: oldItem -> $oldItem, newItem -> $newItem")
            return when {
                oldItem is CoinUiEntity && newItem is CoinUiEntity -> {
                    oldItem.position == newItem.position
                            && oldItem.name == newItem.name
                            && oldItem.price == newItem.price
                            && oldItem.isFavorite == newItem.isFavorite
                }
                else -> false
            }
        }

        override fun getChangePayload(oldItem: DisplayableItem, newItem: DisplayableItem): Any? {
            Log.i("myDebug", "getChangePayload: oldItem -> $oldItem, newItem -> $newItem")
            return when {
                oldItem is CoinUiEntity && newItem is CoinUiEntity -> {
                    if (oldItem.isFavorite != newItem.isFavorite) {
                        newItem.isFavorite
                    } else {
                        super.getChangePayload(oldItem, newItem)
                    }
                }
                else -> super.getChangePayload(oldItem, newItem)

            }
        }
    }

    inner class DiffAdapter : AsyncListDifferDelegationAdapter<DisplayableItem>(
        MyDiffUtilCallBack(),
        adapterDelegatesManager
    )

    private val adapter = DiffAdapter()

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