package com.github.lex090.coreapi.domain

import kotlinx.coroutines.flow.Flow

interface IBaseFlowUseCase<in I : Any, out O : Any> {

    fun execute(data: I): Flow<O>
}