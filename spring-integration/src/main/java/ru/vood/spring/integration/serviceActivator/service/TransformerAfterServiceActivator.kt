package ru.vood.spring.integration.serviceActivator.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TransformerAfterServiceActivator {
    private val log = LoggerFactory.getLogger(TransformerAfterServiceActivator::class.java)

    fun transform(msg: String): String {
        log.info(" TransformerAfterServiceActivator do work with $msg ")
        return msg.toLowerCase()
    }
}