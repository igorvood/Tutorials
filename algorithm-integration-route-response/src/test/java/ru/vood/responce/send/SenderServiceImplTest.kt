package ru.vood.responce.send

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.integration.channel.DirectChannel
import org.springframework.messaging.MessageChannel
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import ru.vood.responce.RouteResponseApplicationTestConfiguration
import ru.vood.responce.common.MetaService
import ru.vood.responce.common.ServiceResolver
import ru.vood.responce.common.ServiceResolverService
import ru.vood.responce.config.AbstractDataSourceTest
import ru.vood.responce.handler.HandleException
import ru.vood.responce.handler.HandleExceptionService
import ru.vood.responce.response.ResponseBuilderServiceImpl

@RunWith(SpringRunner::class)
@ContextConfiguration(classes = [RouteResponseApplicationTestConfiguration::class])
class SenderServiceImplTest : AbstractDataSourceTest() {

    lateinit var channel: MessageChannel
    lateinit var serviceResolver: ServiceResolverService
    lateinit var handleService: HandleExceptionService
    private val responseBuilderServiceImpl = ResponseBuilderServiceImpl()


    @Autowired
    lateinit var senderService: SenderService


    override fun postConstructLocal() {
        super.postConstructLocal()
        channel = DirectChannel()
        serviceResolver = ServiceResolver(jdbcTemplate)
        handleService = HandleException(serviceResolver, responseBuilderServiceImpl)
//        senderService = SenderServiceImpl(channel, serviceResolver, handleService)
    }

    @Test
    fun sendError() {
        senderService.sendError(MetaService.SERVICE1.serviceName, RuntimeException("RuntimeException"), "error 1")
    }
}