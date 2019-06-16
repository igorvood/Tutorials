package ru.vood.responce.exeption

import org.springframework.stereotype.Service
import ru.vood.responce.handler.ServiceName

@Service
class SenderServiceImpl() : SenderService {
    override fun sendError(id: String, serviceName: ServiceName.Companion, codeRet: String, errorText: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}