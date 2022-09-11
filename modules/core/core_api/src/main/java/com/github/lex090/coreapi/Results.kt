package com.github.lex090.coreapi

sealed class ResultOf<out T> {
    data class Success<R : Any>(val data: R) : ResultOf<R>()
    data class Error(
        val exception: Throwable,
        val message: String? = null
    ) : ResultOf<Nothing>()
}