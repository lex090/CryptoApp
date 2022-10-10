package com.github.lex090.baseui.presentation.view

import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.github.lex090.baseui.R
import com.squareup.picasso.Picasso


fun AppCompatImageView.loadCoinImageToIV(imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Picasso
            .get()
            .load(imageUrl)
            .placeholder(R.drawable.round_background_shimmer)
            .error(R.drawable.round_background_shimmer)
            .into(this)
    }
}


fun AppCompatTextView.checkFavorite(
    item: Boolean
) {
    background = if (item) {
        ContextCompat.getDrawable(context, R.drawable.ic_baseline_star_24)
    } else {
        ContextCompat.getDrawable(
            context,
            R.drawable.ic_baseline_star_outline_24
        )
    }
}
