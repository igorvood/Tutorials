package ru.vood.joinpoint2.infrastructure.flow.bean;

import org.springframework.stereotype.Component;
import ru.vood.joinpoint2.infrastructure.flow.context.Bean1ReturnContext;
import ru.vood.joinpoint2.infrastructure.flow.context.Bean1RunContext;

@Component("Bean1")
public class WorkerBeanInterfaceImpl implements WorkerBeanInterface<Bean1RunContext, Bean1ReturnContext> {

    @Override
    public Bean1ReturnContext run(Bean1RunContext bean1RunContext) {
        return new Bean1ReturnContext(bean1RunContext.toString());
    }
}
