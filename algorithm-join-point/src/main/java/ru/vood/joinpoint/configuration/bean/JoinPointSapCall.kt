package ru.vood.joinpoint.configuration.bean

import ru.vood.joinpoint.configuration.infrastructure.context.ContextSapCall
import ru.vood.joinpoint.configuration.infrastructure.context.ContextSapReturn


class JoinPointSapCall : JoinPointBean<ContextSapCall, ContextSapReturn> {
    override fun doWork(inCtx: ContextSapCall): ContextSapReturn {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
