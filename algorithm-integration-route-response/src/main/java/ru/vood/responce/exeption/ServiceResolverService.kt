package ru.vood.responce.exeption

import ru.vood.responce.handler.ServiceName

interface ServiceResolverService {
    fun getServiceById(s: String): Map<ServiceName, Map<String, String>>
}