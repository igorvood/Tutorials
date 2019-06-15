package ru.vood.joinpoint2.infrastructure.bean

import org.springframework.stereotype.Component
import ru.vood.joinpoint2.infrastructure.flow.context.Bean1ReturnContext


@Component("Bean4")
class WorkerBean4 : AbstractWorkerBean<Bean1ReturnContext, Bean1ReturnContext>(Bean1ReturnContext::class.java, Bean1ReturnContext::class.java) {

    override fun doIt(bean2RunContext: Bean1ReturnContext): Bean1ReturnContext {
        return Bean1ReturnContext(bean2RunContext.str.reversed())
    }
}
