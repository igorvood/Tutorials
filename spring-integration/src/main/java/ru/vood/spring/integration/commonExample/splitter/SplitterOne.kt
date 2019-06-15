package ru.vood.spring.integration.commonExample.splitter

import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.Headers
import ru.vood.spring.integration.commonExample.service.VBdObjectTypeEntityService
import ru.vood.spring.integration.commonExample.transformer.transformHeaderMap
import java.util.*

//@Component
class SplitterOne(var vBdObjectTypeEntityService: VBdObjectTypeEntityService) {
    val log = LoggerFactory.getLogger(SplitterOne::class.java)

    fun split(code: String, @Headers headerMap: Map<String, Any>): Collection<String> {

        log.info("Split value $code headerMap-> ${transformHeaderMap(headerMap)} ")

        val toList1 = vBdObjectTypeEntityService.findCodeLike(code).asSequence()
                .map { it.code }
                .toList()
        val toList: ArrayList<String> = ArrayList<String>(toList1)

        return toList
    }

}