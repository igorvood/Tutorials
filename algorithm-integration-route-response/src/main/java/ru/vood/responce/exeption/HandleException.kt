package ru.vood.responce.exeption

import org.apache.commons.logging.LogFactory
import org.springframework.stereotype.Service
import ru.vood.responce.handler.ServiceName

@Service
class HandleException(val serviceResolver: ServiceResolverService, val sender: SenderService) : HandleExceptionService {
    private val logger = LogFactory.getLog(HandleException::class.java)

    override fun handle(id: String, exception: Throwable, errorText: String) {
        val serviceById = serviceResolver.getServiceById(id)
        val exceptionCodeMap = serviceById.second
        var codeRet = exceptionCodeMap[exception.javaClass.name]
        if (codeRet == null)
            codeRet = exceptionCodeMap.get("-DEFUALT-")
        if (codeRet != null) {
            logger.warn("Service ${serviceById.first.serviceName} finish with code $codeRet, error text $errorText stack $exception.stackTrace")
            sender.sendError(id, ServiceName, codeRet, errorText)
        } else {
            logger.error("Service ${serviceById.first.serviceName} does not have default code")
        }
    }
}