package com.github.lex090.coreapi.presentation.uiSate

interface UiStateEntity

sealed class BaseUiState<out T : UiStateEntity> {
    data class Success<R : UiStateEntity>(val data: R) : BaseUiState<R>()

    data class Error(
        val exception: Throwable,
        val message: String? = null
    ) : BaseUiState<Nothing>()

    object Loading : BaseUiState<Nothing>()
}