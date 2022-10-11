package com.github.lex090.coreapi.domain

import kotlinx.coroutines.flow.Flow

interface IBaseEmptyInputFlowUseCase<out O : Any> {

    fun execute(): Flow<O>
}