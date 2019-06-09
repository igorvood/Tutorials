package ru.vood.joinpoint2.infrastructure.bean;

interface WorkerBeanInterface<IN, OUT> {

    OUT doIt(IN in);
}
