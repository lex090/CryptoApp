package com.github.lex090.coreapi.domain

import kotlinx.coroutines.flow.Flow

interface IBaseFlowUseCase<O : Any, T : Any> {

    suspend fun execute(data: O? = null): Flow<T>
}