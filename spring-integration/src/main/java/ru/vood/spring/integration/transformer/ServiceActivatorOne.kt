package ru.vood.spring.integration.transformer

import org.springframework.messaging.handler.annotation.Headers
import org.springframework.stereotype.Component

@Component
open class ServiceActivatorOne {

    fun activate(code: String, @Headers headerMap: Map<String, Object>) {
        println("${Thread.currentThread().name} ${this::class.java.name} -> ServiceActivatorOne value $code headerMap-> ${transformHeaderMap(headerMap)} ")
        //return "%${code.toUpperCase()}%"
    }

}