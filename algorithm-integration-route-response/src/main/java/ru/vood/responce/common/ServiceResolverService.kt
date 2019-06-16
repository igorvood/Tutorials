package ru.vood.responce.common

interface ServiceResolverService {
    fun getServiceById(s: String): Pair<MetaService, ServiceCodeExceptionMap>
}