package ru.vood.responce.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.MessageChannel
import ru.vood.responce.common.MetaService
import ru.vood.responce.route.CommonRequestRouter

@Configuration
open class RouteConfiguration {

    @Bean("commonRequestRouter")
    open fun getCommonRequestRouter(messageChannels: Map<String, MessageChannel>): CommonRequestRouter {
        val serviceToChannel =
                hashMapOf(
                        Pair(MetaService.SERVICE1, messageChannels["service1ResponseChannel"]
                                ?: error("No channel service1ResponseChannel")),
                        Pair(MetaService.SERVICE2, messageChannels["service2ResponseChannel"]
                                ?: error("No channel service2ResponseChannel"))
                )
        return CommonRequestRouter(serviceToChannel)
    }
}