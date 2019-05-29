package ru.vood.joinpoint.configuration.infrastructure.jp

interface JoinPointService<InCtx, OutCtx> {
    fun runJoinPoint(inCtx: InCtx): OutCtx
}