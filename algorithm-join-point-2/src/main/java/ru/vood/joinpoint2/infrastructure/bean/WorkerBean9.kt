package ru.vood.joinpoint2.infrastructure.bean

import org.springframework.stereotype.Component
import ru.vood.joinpoint2.infrastructure.flow.context.Bean1ReturnContext


@Component("Bean9")
class WorkerBean9 : AbstractWorkerBean<Bean1ReturnContext, Bean1ReturnContext>(Bean1ReturnContext::class.java, Bean1ReturnContext::class.java) {

    override fun doIt(bean2RunContext: Bean1ReturnContext): Bean1ReturnContext {
        return Bean1ReturnContext(bean2RunContext.str.reversed())
    }
}
