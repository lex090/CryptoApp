package com.github.lex090.corefactory

import com.github.lex090.corediapi.CoreComponentDependencies
import com.github.lex090.corediimpl.DaggerCoreComponent

object CoreComponentDependenciesFactory {

    fun create(): CoreComponentDependencies = DaggerCoreComponent.factory().create()
}