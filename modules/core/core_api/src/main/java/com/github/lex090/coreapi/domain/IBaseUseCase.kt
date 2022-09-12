package com.github.lex090.coreapi.domain

interface IBaseUseCase<in I : Any, out O : Any> {

    suspend fun execute(data: I): O
}