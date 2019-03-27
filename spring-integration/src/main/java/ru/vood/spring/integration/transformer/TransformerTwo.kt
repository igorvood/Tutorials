package ru.vood.spring.integration.transformer

import org.springframework.messaging.handler.annotation.Headers

//@Component
class TransformerTwo {
    fun transform(code: String, @Headers headerMap: Map<String, Any>): String {
        println("${Thread.currentThread().name} ${this::class.java.name} -> Second Transform value $code headerMap-> ${transformHeaderMap(headerMap)} ")
        return code.toUpperCase()
    }

}