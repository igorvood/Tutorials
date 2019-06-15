package ru.vood.spring.integration.serviceActivator.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.lang.Thread.sleep

@Service
class ServiceServiceActivator {

    private val log = LoggerFactory.getLogger(ServiceServiceActivator::class.java)

    fun run(msg: String): String {
        sleep(2000)
        log.info(" ServiceServiceActivator do work with $msg ")

        return msg.toUpperCase()
    }
}