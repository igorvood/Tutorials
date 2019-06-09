package ru.vood.joinpoint2.infrastructure.mapper

interface ContextMapperService {

    fun toCtx(src: Any): String
    fun <T> fromCtx(json: String): T
}