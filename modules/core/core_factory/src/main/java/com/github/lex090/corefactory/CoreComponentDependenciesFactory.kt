package com.github.lex090.corefactory

import android.content.Context
import com.github.lex090.corediapi.CoreComponentDependencies
import com.github.lex090.corediimpl.DaggerCoreComponent

object CoreComponentDependenciesFactory {

    fun create(applicationContext: Context): CoreComponentDependencies =
        DaggerCoreComponent.factory().create(context = applicationContext)
}