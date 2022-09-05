package com.github.lex090.corenetworkimpl

import java.net.URL

/**
 * Провайдер url для RemoteService
 */
internal interface IUrlProvider {

    /**
     * Предоставление Url
     *
     * @return  Url
     */
    fun provideUrl(): URL
}