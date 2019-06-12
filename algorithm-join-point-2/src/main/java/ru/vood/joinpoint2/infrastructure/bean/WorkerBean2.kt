package ru.vood.joinpoint2.infrastructure.bean

import org.springframework.stereotype.Component
import ru.vood.joinpoint2.infrastructure.flow.context.Bean2ReturnContext
import ru.vood.joinpoint2.infrastructure.flow.context.Bean2RunContext


@Component("Bean2")
class WorkerBean2 : AbstractWorkerBean<Bean2RunContext, Bean2ReturnContext>(Bean2RunContext::class.java, Bean2ReturnContext::class.java) {

    override fun doIt(bean2RunContext: Bean2RunContext): Bean2ReturnContext {
        return Bean2ReturnContext(java.lang.Long.valueOf(bean2RunContext.cnt))
    }
}
