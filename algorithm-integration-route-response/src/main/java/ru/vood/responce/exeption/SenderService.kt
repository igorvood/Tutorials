package ru.vood.responce.exeption

import ru.vood.responce.handler.ServiceName

interface SenderService {
    fun sendError(id: String, serviceName: ServiceName.Companion, codeRet: String, errorText: String)
}
