package ru.vood.joinpoint2.infrastructure.flow

import ru.vood.joinpoint2.infrastructure.flow.data.JoinPointData

interface ActivityJoinPointOrderRunDaoService {

    fun nextJoinPoints(id: Int, joinPoint: String, flowType: String): Map<String, JoinPointData>

    fun prevJoinPoints(id: Int, joinPoint: String, flowType: String): Map<String, JoinPointData>

    fun getFirstJoinPoint(id: Int, flowType: String): Map<String, JoinPointData>

    fun getJoinPoint(id: Int, joinPoint: String): JoinPointData
}