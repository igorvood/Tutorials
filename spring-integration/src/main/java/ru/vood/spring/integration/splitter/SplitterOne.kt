package ru.vood.spring.integration.splitter

import org.springframework.messaging.handler.annotation.Headers
import org.springframework.stereotype.Component
import ru.vood.spring.integration.service.VBdObjectTypeEntityService
import ru.vood.spring.integration.transformer.transformHeaderMap
import java.util.*

@Component
class SplitterOne(var vBdObjectTypeEntityService: VBdObjectTypeEntityService) {

    fun split(code: String, @Headers headerMap: Map<String, Object>): Collection<String> {

        println("${Thread.currentThread().name} ${this::class.java.name} -> Split value $code headerMap-> ${transformHeaderMap(headerMap)} ")

        val toList1 = vBdObjectTypeEntityService.findCodeLike(code).asSequence()
                .map { it.code }
                .toList()
        val toList: ArrayList<String> = ArrayList<String>(toList1)

        return toList
    }

}