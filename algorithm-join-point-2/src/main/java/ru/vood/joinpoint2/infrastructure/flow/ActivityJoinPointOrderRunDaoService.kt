package ru.vood.joinpoint2.infrastructure.flow

import ru.vood.joinpoint2.infrastructure.flow.data.JoinPointContextData

interface ActivityJoinPointOrderRunDaoService {

    fun nextJoinPoints(id: Long, joinPoint: String): Map<String, JoinPointContextData>

    fun prevJoinPoints(id: Long, joinPoint: String): Map<String, JoinPointContextData>

    fun getFirstJoinPoint(id: Long, flowType: String): JoinPointContextData

    fun getJoinPoint(id: Long, joinPoint: String): JoinPointContextData

    fun insertContext(id: Long, joinPoint: String, kindContext: KindContext, ctx: String)

}