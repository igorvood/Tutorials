package ru.vood.spring.integration.commonExample.transformer

import org.slf4j.LoggerFactory
import org.springframework.integration.transformer.GenericTransformer

//@Component
open class TransformerOne : GenericTransformer<String, String> {
    val log = LoggerFactory.getLogger(TransformerOne::class.java)
    override fun transform(source: String?): String {
        log.info("Transform string->$source")
        return "${source?.toUpperCase()}"
    }

/*
    fun transform(code: String, @Headers headerMap: Map<String, Any>): String {
        println("${Thread.currentThread().name} ${this::class.java.name} -> Transform value $code headerMap-> ${transformHeaderMap(headerMap)} ")
        return "%${code.toUpperCase()}%"
    }
*/

}

