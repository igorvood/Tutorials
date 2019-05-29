package ru.vood.joinpoint.configuration.infrastructure.context

interface ContextService<T> {
    fun getCtx(): T
}
