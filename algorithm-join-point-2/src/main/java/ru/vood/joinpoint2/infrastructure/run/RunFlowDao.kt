package ru.vood.joinpoint2.infrastructure.run

interface RunFlowDao {
    fun createRunnableFlow(ft: String, inCtx: String): String

}