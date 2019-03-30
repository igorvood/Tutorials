package ru.vood.spring.integration.transformer

import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.Headers

//@Component
class TransformerTwo {
    val log = LoggerFactory.getLogger(TransformerTwo::class.java)
    fun transform(code: String, @Headers headerMap: Map<String, Any>): String {
        log.info(" Second Transform value $code headerMap-> ${transformHeaderMap(headerMap)} ")
        return code.toUpperCase()
    }

}