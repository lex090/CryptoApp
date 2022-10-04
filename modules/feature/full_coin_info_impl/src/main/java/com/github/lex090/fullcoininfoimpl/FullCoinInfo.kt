package com.github.lex090.fullcoininfoimpl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.lex090.fullcoininfoimpl.databinding.FullCoinInfoBsdfLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FullCoinInfo : BottomSheetDialogFragment() {

    private var _viewBinding: FullCoinInfoBsdfLayoutBinding? = null
    private val viewBinding: FullCoinInfoBsdfLayoutBinding
        get() = _viewBinding!!


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
        return viewBinding.root
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }
}