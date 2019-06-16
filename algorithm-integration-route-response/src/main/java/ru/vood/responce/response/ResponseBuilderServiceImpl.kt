package ru.vood.responce.response

import org.springframework.stereotype.Service
import ru.vood.responce.common.MetaService

@Service
class ResponseBuilderServiceImpl : ResponseBuilderService {
    override fun buildError(id: String, service: MetaService, exception: Throwable, errorText: String): Pair<MetaService, String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}