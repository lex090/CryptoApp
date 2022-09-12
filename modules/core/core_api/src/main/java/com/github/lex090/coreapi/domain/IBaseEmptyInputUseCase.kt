package com.github.lex090.coreapi.domain


interface IBaseEmptyInputUseCase<out O : Any> {

    suspend fun execute(): O
}