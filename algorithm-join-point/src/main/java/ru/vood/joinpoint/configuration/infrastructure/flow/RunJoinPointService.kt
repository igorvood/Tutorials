package ru.vood.joinpoint.configuration.infrastructure.flow

import ru.vood.joinpoint.configuration.infrastructure.jp.BeanInterface

interface RunJoinPointService<IN_CTX, OUT_CTX> {
    fun run(inCtx: IN_CTX, bean: BeanInterface<IN_CTX, OUT_CTX>): OUT_CTX

    fun run(inCtx: IN_CTX, beanName: String): OUT_CTX
}