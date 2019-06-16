package ru.vood.responce.response

import ru.vood.responce.common.MetaService

interface ResponseBuilderService {
    fun buildError(id: String, service: MetaService, exception: Throwable, errorText: String): Pair<MetaService, String>

}
