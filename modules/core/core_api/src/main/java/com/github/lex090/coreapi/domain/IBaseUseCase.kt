package com.github.lex090.coreapi.domain

interface IBaseUseCase<T : Any> {

    suspend fun execute(): T
}