package com.github.lex090.fullcoininfoimpl.data

import com.tinder.scarlet.Lifecycle
import com.tinder.scarlet.lifecycle.LifecycleRegistry
import javax.inject.Inject

class ScarletLifecycle @Inject constructor(
    private val lifecycleRegistry: LifecycleRegistry
) : Lifecycle by lifecycleRegistry {

    fun start() {
        lifecycleRegistry.onNext(Lifecycle.State.Started)
    }

    fun stop() {
        lifecycleRegistry.onNext(Lifecycle.State.Destroyed)
    }
}