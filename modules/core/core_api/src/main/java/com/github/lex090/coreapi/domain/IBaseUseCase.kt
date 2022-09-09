package com.github.lex090.coreapi.domain

interface IBaseUseCase<out T : Any> {

    suspend fun execute(): T
}