package com.github.lex090.coreapi.domain

import kotlinx.coroutines.flow.Flow

interface IBaseFlowUseCase<T : Any> {

    suspend fun execute(): Flow<T>
}