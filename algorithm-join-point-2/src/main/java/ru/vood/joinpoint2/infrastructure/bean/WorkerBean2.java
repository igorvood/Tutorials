package ru.vood.joinpoint2.infrastructure.bean;

import org.springframework.stereotype.Component;
import ru.vood.joinpoint2.infrastructure.flow.context.Bean2ReturnContext;
import ru.vood.joinpoint2.infrastructure.flow.context.Bean2RunContext;

@Component("Bean2")
public class WorkerBean2 extends AbstractWorkerBean<Bean2RunContext, Bean2ReturnContext> {


    public WorkerBean2() {
        super(Bean2RunContext.class, Bean2ReturnContext.class);
    }


    @Override
    public Bean2ReturnContext doIt(Bean2RunContext bean2RunContext) {
        return new Bean2ReturnContext(Long.valueOf(bean2RunContext.getCnt()));
    }
}
