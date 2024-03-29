package ru.vood.spring.integration.commonExample.transformer

import java.text.MessageFormat

fun transformHeaderMap(headerMap: Map<String, Any>): String {
    var s = ""
    for (key in headerMap.keys) {
        val value = headerMap[key]
        if (key !== "sequenceSize" && key !== "timestamp")
            s += MessageFormat.format("{0} : {1}. ", key, value)
    }
    return s
}