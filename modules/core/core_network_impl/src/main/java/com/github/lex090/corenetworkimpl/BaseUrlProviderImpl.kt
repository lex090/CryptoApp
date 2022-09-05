package com.github.lex090.corenetworkimpl

import java.net.URL
import javax.inject.Inject

internal class BaseUrlProviderImpl @Inject constructor() : IUrlProvider {

    override fun provideUrl(): URL =
        URL("https://pro-api.coingecko.com/api/v3/")
}