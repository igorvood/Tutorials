package ru.vood.joinpoint2.infrastructure.bean;

import org.springframework.stereotype.Component;
import ru.vood.joinpoint2.infrastructure.flow.context.Bean1ReturnContext;
import ru.vood.joinpoint2.infrastructure.flow.context.Bean1RunContext;

@Component("Bean2")
public class WorkerBean2 extends AbstractWorkerBean<Bean1RunContext, Bean1ReturnContext> {


    public WorkerBean2() {
        super(Bean1RunContext.class, Bean1ReturnContext.class);
    }

    @Override
    public Bean1ReturnContext doIt(Bean1RunContext bean1RunContext) {
        return new Bean1ReturnContext(bean1RunContext.toString());
    }
}
