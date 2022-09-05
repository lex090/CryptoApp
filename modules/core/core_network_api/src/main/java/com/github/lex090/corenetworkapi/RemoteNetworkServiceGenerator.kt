package com.github.lex090.corenetworkapi

import kotlin.reflect.KClass

/**
 * Компонент создающий реализации к Retrofit сервисам
 */
interface RemoteNetworkServiceGenerator {

    /**
     * Создание реализации к Retrofit сервису
     *
     * @param remoteService     Интерфейс Retrofit сервиса
     * @return                  Конкретный экземпляр реализующий переданный в аргументах интерфейс
     */
    fun <ServiceType : Any> create(remoteService: KClass<ServiceType>): ServiceType
}