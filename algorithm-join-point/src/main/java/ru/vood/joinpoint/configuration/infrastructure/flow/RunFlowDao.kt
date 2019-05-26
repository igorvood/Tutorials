package ru.vood.joinpoint.configuration.infrastructure.flow

interface RunFlowDao {
    fun runFlow(ft: FlowType): String
}