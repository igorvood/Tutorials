package ru.vood.joinpoint2.infrastructure.flow.bean;

interface WorkerBeanInterface<IN, OUT> {

    OUT run(IN in);
}
