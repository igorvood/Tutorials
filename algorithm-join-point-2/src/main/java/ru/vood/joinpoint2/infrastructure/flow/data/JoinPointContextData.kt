package ru.vood.joinpoint2.infrastructure.flow.data

import java.sql.Timestamp

data class JoinPointContextData(
        val id: Long,
        val joinPoint: String,
        val expireAt: Timestamp,
        val timoutDetectedAt: Timestamp?,
        val dateBegin: Timestamp?,
        val dateEnd: Timestamp?,
        val state: String,
        val isClosed: Boolean,
        val beanName: String,
        val runContextId: String,
        val returnContextId: String,
        val runContext: String?,
        val returnContext: String?,
        val isAsyncRun: Boolean

        //flow_id, join_point, expire_at, timout_detected_at, date_beg, state, is_closed, bean_name, run_context_id, return_context_id, run_context, return_context
)