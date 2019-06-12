package ru.vood.joinpoint2.infrastructure.flow

import ru.vood.joinpoint2.infrastructure.flow.data.JoinPointContextData
import ru.vood.joinpoint2.infrastructure.flow.data.JoinPointData

interface ActivityJoinPointOrderRunDaoService {

    fun nextJoinPoints(id: Long, joinPoint: String): Map<String, JoinPointContextData>

    fun prevJoinPoints(id: Long, joinPoint: String): Map<String, JoinPointContextData>

    fun getFirstJoinPoint(id: Long, flowType: String): Map<String, JoinPointData>

    fun getJoinPoint(id: Long, joinPoint: String): JoinPointContextData

    fun insertReturnContext(id: Long, joinPoint: String, ctx: String)

    fun insertRunContext(id: Long, joinPoint: String, ctx: String)
}