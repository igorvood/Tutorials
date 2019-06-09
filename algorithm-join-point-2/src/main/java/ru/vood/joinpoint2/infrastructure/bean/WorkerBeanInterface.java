package ru.vood.joinpoint2.infrastructure.bean;

public interface WorkerBeanInterface<IN, OUT> {

    OUT doIt(IN in);

    IN getObjectFromContext(String json);

    String getContextFormObject(OUT outCtx);

}
