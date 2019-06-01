package ru.vood.joinpoint.configuration.infrastructure.flow

interface RunFlowDao {
    fun createRunnableFlow(ft: FlowType): String

    fun runFirstFlowBean(ft: FlowType, inCtx: String): String

}