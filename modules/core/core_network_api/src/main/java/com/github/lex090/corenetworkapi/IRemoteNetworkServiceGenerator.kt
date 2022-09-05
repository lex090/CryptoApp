package com.github.lex090.corenetworkapi

/**
 * Компонент создающий реализации к Retrofit сервисам
 */
interface IRemoteNetworkServiceGenerator {

    /**
     * Создание реализации к Retrofit сервису
     *
     * @param remoteService     Интерфейс Retrofit сервиса
     * @return                  Конкретный экземпляр реализующий переданный в аргументах интерфейс
     */
    fun <ServiceType : Any> create(remoteService: Class<ServiceType>): ServiceType
}