package ru.vood.spring.integration.serviceActivator.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.integration.support.MessageBuilder
import org.springframework.messaging.MessageChannel
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class ScheduledServiceActivator(@Autowired
                                @Qualifier("serviceActivatorRequestChannel_1")
                                var chanelSecond: MessageChannel,
                                var s: ServiceServiceActivator
) {
    private val log = LoggerFactory.getLogger(ScheduledServiceActivator::class.java)

    @Scheduled(fixedRate = 5000)
    fun sendMsg() {
        log.info("Sending orders to input channel")
        chanelSecond.send(MessageBuilder.withPayload("Sin Ti").build())
    }

    @Scheduled(fixedRate = 1000)
    fun runServiceActivator() {
        s.run("Мой Лабиринт")
    }


}