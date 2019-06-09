package ru.vood.joinpoint2.infrastructure.flow

import ru.vood.joinpoint2.infrastructure.flow.data.DictionaryJoinPointData

interface DictionaryJoinPointOrderRunDaoService {

    fun nextJoinPoints(joinPoint: String, flowType: String): Map<String, DictionaryJoinPointData>

    fun prevJoinPoints(joinPoint: String, flowType: String): Map<String, DictionaryJoinPointData>

    fun getFirstJoinPoint(flowType: String): Map<String, DictionaryJoinPointData>
}