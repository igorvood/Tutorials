package ru.vood.spring.integration.transformer

import org.springframework.integration.transformer.GenericTransformer

//@Component
open class TransformerOne(/*var vBdObjectTypeEntityService: VBdObjectTypeEntityService*/) : GenericTransformer<String, String> {
    override fun transform(source: String?): String {
        return "%${source?.toUpperCase()}%"
    }

/*
    fun transform(code: String, @Headers headerMap: Map<String, Any>): String {
        println("${Thread.currentThread().name} ${this::class.java.name} -> Transform value $code headerMap-> ${transformHeaderMap(headerMap)} ")
        return "%${code.toUpperCase()}%"
    }
*/

}

