package ru.vood.spring.integration.serviceActivator.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ServiceServiceActivator {

    private val log = LoggerFactory.getLogger(ServiceServiceActivator::class.java)

    fun run(msg: String): String {
        log.info(" ServiceServiceActivator do work with $msg ")
        return msg.toUpperCase()
    }
}