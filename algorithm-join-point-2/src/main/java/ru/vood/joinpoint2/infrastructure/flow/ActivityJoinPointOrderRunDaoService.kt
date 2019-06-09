package ru.vood.joinpoint2.infrastructure.flow

import ru.vood.joinpoint2.infrastructure.flow.data.JoinPointData

interface ActivityJoinPointOrderRunDaoService {

    fun nextJoinPoints(id: Long, joinPoint: String, flowType: String): Map<String, JoinPointData>

    fun prevJoinPoints(id: Long, joinPoint: String, flowType: String): Map<String, JoinPointData>

    fun getFirstJoinPoint(id: Long, flowType: String): Map<String, JoinPointData>

    fun getJoinPoint(id: Long, joinPoint: String): JoinPointData
}