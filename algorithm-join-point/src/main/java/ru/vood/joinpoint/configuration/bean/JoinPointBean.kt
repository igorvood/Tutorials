package ru.vood.joinpoint.configuration.bean

import ru.vood.joinpoint.configuration.infrastructure.context.ContextService

interface JoinPointBean<in InCtx : ContextService<*>, out OutCtx : ContextService<*>> {

    fun doWork(inCtx: InCtx): OutCtx
}