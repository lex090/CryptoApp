package com.github.lex090.corenetworkimpl

import javax.inject.Qualifier

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
internal annotation class ConnectionTimeout

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
internal annotation class CallTimeout

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
internal annotation class ReadTimeout