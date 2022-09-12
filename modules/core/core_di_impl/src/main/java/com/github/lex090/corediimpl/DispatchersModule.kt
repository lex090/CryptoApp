package com.github.lex090.corediimpl

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext


@Module
internal object DispatchersModule {

    @Provides
    fun provideDispatcherIo(): CoroutineContext = Dispatchers.IO
}