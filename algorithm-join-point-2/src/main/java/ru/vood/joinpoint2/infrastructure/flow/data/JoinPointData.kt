package ru.vood.joinpoint2.infrastructure.flow.data

data class JoinPointData(
        val lv: Int,
        val cycl: Int,
        val flow: String,
        val runner: String?,
        val is_async_run: Int?,
        val runnable: String,
        val syntheticId: String,
        val parent: String,
        val runBean: String?,
        val runBeanInCtx: String?,
        val runBeanRetCtx: String?,
        val runBeanTimeout: Int?,
        val rblBean: String,
        val rblBeanInCtx: String,
        val rblBeanRetCtx: String,
        val rblBeanTimeout: Int,
        val id: Long,

        val runnerRunContext: String?,
        val runnerRetContext: String?,
        val runnableRunContext: String?,
        val runnableRetContext: String?
)