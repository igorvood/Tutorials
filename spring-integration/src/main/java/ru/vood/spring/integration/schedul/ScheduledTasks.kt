package ru.vood.spring.integration.schedul

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.integration.support.MessageBuilder
import org.springframework.messaging.MessageChannel
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component


@Component
class ScheduledTasks(@Autowired
                     @Qualifier("requestChannel_1")
                     var chanel: MessageChannel,
                     @Autowired
                     @Qualifier("requestChannel")
                     var chanelSecond: MessageChannel
) {

    private val log = LoggerFactory.getLogger(ScheduledTasks::class.java)

    @Scheduled(fixedRate = 5000)
    fun reportCurrentTime() {
        log.info("Sending orders to input channel")
        //chanel.send(MessageBuilder.withPayload("ta").build())

        chanelSecond.send(MessageBuilder.withPayload("t").build())
    }
}