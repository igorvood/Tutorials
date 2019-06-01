package ru.vood.joinpoint.configuration.infrastructure.flow

interface FlowService {

    fun runFlow(ft: FlowType, inCtx: Any)

    fun runFlow(ft: FlowType)
}