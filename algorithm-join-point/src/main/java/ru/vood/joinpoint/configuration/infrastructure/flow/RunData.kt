package ru.vood.joinpoint.configuration.infrastructure.flow

data class RunData(
        val level: Int,
        val path: String,
        val runner: String,
        val runnerFlow: String,
        val runnable: String,
        val runnableFlow: String,
        val runBean: String,
        val runBeanInputContext: String,
        val runBeanReturnContext: String,
        val runBeanTimeout: Long,
        val runnableBean: String,
        val runnableBeanInputContext: String,
        val runnableBeanReturnContext: String,
        val runnableBeanTimeout: Long
)
