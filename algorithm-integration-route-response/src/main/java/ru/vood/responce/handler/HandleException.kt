package ru.vood.responce.handler

import org.apache.commons.logging.LogFactory
import org.springframework.stereotype.Service
import ru.vood.responce.common.MetaService
import ru.vood.responce.common.ServiceCodeExceptionMap
import ru.vood.responce.common.ServiceResolverService
import ru.vood.responce.response.ResponseBuilderService

@Service
class HandleException(
        private val serviceResolver: ServiceResolverService,
        private val responseBuilderService: ResponseBuilderService
) : HandleExceptionService {
    private val logger = LogFactory.getLog(HandleException::class.java)

    override fun handle(id: String, exception: Throwable, errorText: String): Pair<MetaService, String> {
        val serviceById = serviceResolver.getServiceById(id)
        return handle(id, serviceById, exception, errorText)
    }

    override fun handle(id: String, serviceInfo: Pair<MetaService, ServiceCodeExceptionMap>, exception: Throwable, errorText: String): Pair<MetaService, String> {
        val exceptionCodeMap = serviceInfo.second
        var codeRet = exceptionCodeMap[exception.javaClass.name]
        if (codeRet == null)
            codeRet = exceptionCodeMap["-DEFUALT-"]
        if (codeRet != null) {
            logger.warn("Service ${serviceInfo.first.serviceName} finish with code $codeRet, error text $errorText stack $exception.stackTrace")
        } else {
            logger.error("Service ${serviceInfo.first.serviceName} does not have default code")
        }
        return responseBuilderService.buildError(id, serviceInfo.first, exception, errorText)
    }
}