package ru.vood.joinpoint.configuration.infrastructure.flow;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.vood.joinpoint.configuration.infrastructure.jp.BeanInterface;

import java.util.Map;

@Service
public class RunJoinPointServiceImpl<IN_CTX, OUT_CTX> implements RunJoinPointService<IN_CTX, OUT_CTX> {

    private final Map<String, BeanInterface> mapBeanInterface;

    public RunJoinPointServiceImpl(Map<String, BeanInterface> mapBeanInterface) {
        this.mapBeanInterface = mapBeanInterface;
    }

    @Override
    public OUT_CTX run(IN_CTX in_ctx, @NotNull BeanInterface<IN_CTX, OUT_CTX> bean) {
        return bean.run(in_ctx);
    }

    @Override
    public OUT_CTX run(IN_CTX in_ctx, @NotNull String beanName) {
        return (OUT_CTX) run(in_ctx, mapBeanInterface.get(beanName));
    }
}
