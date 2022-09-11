package com.github.lex090.coreapi.domain

interface IBaseUseCase<I : Any, O : Any> {

    suspend fun execute(data: I? = null): O
}