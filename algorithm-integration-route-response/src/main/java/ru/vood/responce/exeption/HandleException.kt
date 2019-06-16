package ru.vood.responce.exeption

import org.springframework.stereotype.Service

@Service
class HandleException(val serviceResolver: ServiceResolverService) : HandleExceptionService {
    override fun handle(id: String, exception: Throwable, errorText: String) {
        val serviceById = serviceResolver.getServiceById(id)
        val s = serviceById.second[exception.javaClass.name]
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}