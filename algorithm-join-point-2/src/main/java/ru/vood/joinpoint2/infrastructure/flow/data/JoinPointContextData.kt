package ru.vood.joinpoint2.infrastructure.flow.data

import java.sql.Timestamp

data class JoinPointContextData(
        val id: Long,
        val joinPoint: String,
        val expireAt: Timestamp,
        val timoutDetectedAt: Timestamp,
        val dateBegin: Timestamp,
        val dateEnd: Timestamp,
        val state: String,
        val isClosed: Boolean,
        val isAsyncRun: String,
        val beanName: String,
        val runContextId: String,
        val returnContextId: String,
        val runContext: String,
        val returnContext: String
)