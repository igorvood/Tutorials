package ru.vood.joinpoint2.infrastructure.flow.data

data class DictionaryJoinPointData(
        val lv: Int,
        val cycl: Int,
        val flow: String,
        val runner: String?,
        val isAsyncRun: Int?,
        val runnable: String,
        val id: String,
        val parent: String,
        val runBean: String?,
        val runBeanInCtx: String?,
        val runBeanRetCtx: String?,
        val runBeanTimeout: Int?,
        val rblBean: String,
        val rblBeanInCtx: String,
        val rblBeanRetCtx: String,
        val rblBeanTimeout: Int
)