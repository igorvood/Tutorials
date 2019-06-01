package ru.vood.joinpoint.configuration.infrastructure.jp

interface BeanInterface<InCtx, OutCtx> {
    fun run(inCtx: InCtx): OutCtx
}