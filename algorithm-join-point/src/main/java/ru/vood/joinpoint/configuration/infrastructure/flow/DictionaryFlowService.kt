package ru.vood.joinpoint.configuration.infrastructure.flow

interface DictionaryFlowService {

    fun getFirstJoinPoint(flowType: FlowType): DictionaryJoinPointData
    fun getNextJoinPoint(flowType: FlowType, currentBean: String): Map<String, DictionaryJoinPointData>
}