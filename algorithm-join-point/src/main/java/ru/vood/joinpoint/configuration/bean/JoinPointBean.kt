package ru.vood.joinpoint.configuration.bean

import ru.vood.joinpoint.configuration.context.KContextType

interface JoinPointBean<in InCtx : KContextType<*>, out OutCtx : KContextType<*>> {

    fun doWork(inCtx: InCtx): OutCtx
}