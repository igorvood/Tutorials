package ru.vood.responce.exeption

import org.springframework.messaging.MessageChannel
import ru.vood.responce.handler.ServiceName


class CommonRequestRouter(val seviceToChannel: HashMap<ServiceName, MessageChannel>) {

    fun route(serviceName: ServiceName) {

    }
}