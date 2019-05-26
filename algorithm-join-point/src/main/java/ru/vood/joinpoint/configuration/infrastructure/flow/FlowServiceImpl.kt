package ru.vood.joinpoint.configuration.infrastructure.flow

import org.springframework.stereotype.Service

@Service
open class FlowServiceImpl(private val runFlowDao: RunFlowDao) : FlowService {

    override fun runFlow(ft: FlowType) {
        runFlowDao.runFlow(ft)
    }
}