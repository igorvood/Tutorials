package ru.vood.joinpoint.configuration.infrastructure.flow

import org.junit.Assert
import org.junit.Test
import ru.vood.joinpoint.config.AbstractJoinPointDataSourceTest

class DictionaryFlowServiceImplTest : AbstractJoinPointDataSourceTest() {

    lateinit var dictionaryFlowServiceImpl: DictionaryFlowService

    override fun postConstructLocal() {
        super.postConstructLocal()
        dictionaryFlowServiceImpl = DictionaryFlowServiceImpl(jdbcTemplate)
    }

    @Test
    fun getFirstJoinPoint() {
        val firstJoinPoint = dictionaryFlowServiceImpl.getFirstJoinPoint(FlowType.FLOW_1)
        Assert.assertNotNull(firstJoinPoint)
        Assert.assertEquals(FlowType.FLOW_1.flowName, firstJoinPoint.flow)
    }

    @Test
    fun getNextJoinPoint() {
        val nextJoinPoint = dictionaryFlowServiceImpl.getNextJoinPoint(FlowType.FLOW_1, "Bean2")
        println(nextJoinPoint)

    }
}