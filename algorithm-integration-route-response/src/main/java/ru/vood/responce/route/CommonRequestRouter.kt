package ru.vood.responce.route

import org.springframework.integration.channel.DirectChannel
import org.springframework.messaging.MessageChannel
import ru.vood.responce.common.MetaService


class CommonRequestRouter(val seviceToChannel: HashMap<MetaService, MessageChannel>) {

    fun route(metaService: MetaService): MessageChannel {
        return DirectChannel()
    }

//    fun route(metaService: MetaService)  {
//
//    }

}