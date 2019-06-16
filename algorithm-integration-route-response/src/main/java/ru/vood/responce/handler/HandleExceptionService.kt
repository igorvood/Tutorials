package ru.vood.responce.handler

import ru.vood.responce.common.MetaService
import ru.vood.responce.common.ServiceCodeExceptionMap

interface HandleExceptionService {

    fun handle(id: String, serviceInfo: Pair<MetaService, ServiceCodeExceptionMap>, exception: Throwable, errorText: String): Pair<MetaService, String>
    fun handle(id: String, exception: Throwable, errorText: String): Pair<MetaService, String>
}