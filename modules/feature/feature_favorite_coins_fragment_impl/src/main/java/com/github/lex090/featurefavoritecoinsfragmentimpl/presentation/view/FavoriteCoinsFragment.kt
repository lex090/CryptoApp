package com.github.lex090.featurefavoritecoinsfragmentimpl.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.lex090.corediapi.AppDependenciesProvidersHolder
import com.github.lex090.featurefavoritecoinsfragmentimpl.databinding.FragmentFavoriteCoinsBinding
import com.github.lex090.featurefavoritecoinsfragmentimpl.di.DaggerFavoriteCoinsFragmentComponent
import com.github.lex090.featurefavoritecoinsfragmentimpl.presentation.viewmodel.FavoriteCoinsViewModel
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
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }

    private fun initRecyclerView() {
//        viewBinding.rvFavoritesList.adapter = adapter
        viewBinding.rvFavoritesList.layoutManager = LinearLayoutManager(context)
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