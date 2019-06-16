package ru.vood.responce.send

interface SenderService {
    fun sendError(id: String, exception: Throwable, errorText: String)
    // fun sendError(id: String, metaService: MetaService.Companion, codeRet: String, errorText: String)
}
