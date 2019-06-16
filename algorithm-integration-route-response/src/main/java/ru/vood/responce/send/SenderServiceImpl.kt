package ru.vood.responce.send

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service
import ru.vood.responce.common.ServiceResolverService
import ru.vood.responce.handler.HandleExceptionService

@Service
class SenderServiceImpl(
        @Autowired
        @Qualifier("commonResponseChannel")
        val channel: MessageChannel,
        private val serviceResolver: ServiceResolverService,
        private val handleService: HandleExceptionService

) : SenderService {

    override fun sendError(id: String, exception: Throwable, errorText: String) {
        val service = serviceResolver.getServiceById(id)
        val handle = handleService.handle(id, service, exception, errorText)
        val msg = MessageBuilder.withPayload(handle).build()
        channel.send(msg)
    }

}