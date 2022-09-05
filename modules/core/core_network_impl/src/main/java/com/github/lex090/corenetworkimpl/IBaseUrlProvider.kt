package com.github.lex090.corenetworkimpl

import java.net.URL

internal interface IBaseUrlProvider {

    fun provideBaseUrl(): URL
}