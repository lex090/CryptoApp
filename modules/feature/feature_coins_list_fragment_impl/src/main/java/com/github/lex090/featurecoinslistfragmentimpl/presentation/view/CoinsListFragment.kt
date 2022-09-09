package com.github.lex090.featurecoinslistfragmentimpl.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.github.lex090.corenetworkapi.ResultOf
import com.github.lex090.featurecoinslistfragmentimpl.databinding.FragmentCoinsListBinding
import com.github.lex090.featurecoinslistfragmentimpl.presentation.viewmodel.CoinListViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CoinsListFragment : Fragment() {

    private var _viewBinding: FragmentCoinsListBinding? = null
    private val viewBinding: FragmentCoinsListBinding
        get() = _viewBinding!!

    private val viewModel by viewModels<CoinListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentCoinsListBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    }

    private fun showError(error: ResultOf.Error) {

    }
}