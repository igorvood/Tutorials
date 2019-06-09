package ru.vood.joinpoint2.infrastructure.flow

import ru.vood.joinpoint2.infrastructure.flow.data.DictionaryJoinPointData

interface ActivityJoinPointOrderRunDaoService {

    fun nextJoinPoints(id: Int, joinPoint: String, flowType: String): Map<String, DictionaryJoinPointData>

    fun prevJoinPoints(id: Int, joinPoint: String, flowType: String): Map<String, DictionaryJoinPointData>

    fun getFirstJoinPoint(id: Int, flowType: String): Map<String, DictionaryJoinPointData>
}