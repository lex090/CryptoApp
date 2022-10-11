package com.github.lex090.featureappflowfragmentimpl.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.github.lex090.corediapi.AppDependenciesProvidersHolder
import com.github.lex090.featureappflowfragmentimpl.R
import com.github.lex090.featureappflowfragmentimpl.databinding.FragmentAppFlowBinding
import com.github.lex090.featureappflowfragmentimpl.di.DaggerAppFlowFragmentComponent
import com.github.lex090.featureappflowfragmentimpl.presentation.viewmodel.AppFlowViewModel
import com.github.lex090.featurefavoritecoinsfragmentapi.R.id.nav_graph_favorite_coins_fragment
import kotlinx.coroutines.launch
import javax.inject.Inject

class AppFlowFragment : Fragment() {

    private var _viewBinding: FragmentAppFlowBinding? = null
    private val viewBinding: FragmentAppFlowBinding
        get() = _viewBinding!!

    @Inject
    lateinit var viewModelFactory: AppFlowViewModel.Factory

    private val viewModel by viewModels<AppFlowViewModel> {
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
        _viewBinding = FragmentAppFlowBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBottomNavigationBar()
        initSubscriptionOnViewModel()
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }

    private fun initBottomNavigationBar() {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.mainFlowFragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController

        viewBinding.bottomNavigationView.elevation = 0f
        viewBinding.bottomNavigationView.setupWithNavController(navController = navController)
    }

    private fun initSubscriptionOnViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel
                    .favoritesCoinsCount
                    .collect(::processFavoritesCount)
            }
        }
    }

    private fun processFavoritesCount(favoritesCont: Int) {
        val badge = viewBinding
            .bottomNavigationView
            .getOrCreateBadge(nav_graph_favorite_coins_fragment)
        badge.backgroundColor = ContextCompat.getColor(requireContext(), R.color.grayMain)
        badge.isVisible = favoritesCont > 0
        badge.number = favoritesCont
    }

    private fun injectDependencies() {
        val dependenciesProvider = this.activity?.application as? AppDependenciesProvidersHolder
        dependenciesProvider?.let {
            val component = DaggerAppFlowFragmentComponent
                .factory()
                .create(
                    dependencies = it.getProvider()
                )
            component.inject(this)
        }
    }
}