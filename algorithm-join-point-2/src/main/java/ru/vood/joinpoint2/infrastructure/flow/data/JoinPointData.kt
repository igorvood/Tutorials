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
        val run_bean: String?,
        val run_bean_in_ctx: String?,
        val run_bean_ret_ctx: String?,
        val run_bean_timeout: Int?,
        val rbl_bean: String,
        val rbl_bean_in_ctx: String,
        val rbl_bean_ret_ctx: String,
        val rbl_bean_timeout: Int,
        val id: Int,

        val runner_run_context: String?,
        val runner_ret_context: String?,
        val runnable_run_context: String?,
        val runnable_ret_context: String?
)