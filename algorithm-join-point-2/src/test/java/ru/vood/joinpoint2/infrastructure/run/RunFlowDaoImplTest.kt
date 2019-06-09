package ru.vood.joinpoint2.infrastructure.run

import org.junit.Assert
import org.junit.Test
import org.springframework.transaction.annotation.Transactional
import ru.vood.joinpoint2.config.AbstractJoinPointDataSourceTest
import ru.vood.joinpoint2.infrastructure.flow.FlowType

@Transactional
open class RunFlowDaoImplTest : AbstractJoinPointDataSourceTest() {

    lateinit var runFlowDaoImpl: RunFlowDao

    override fun isThisTestStdSqlResources(): Boolean {
        return true
    }

    override fun postConstructLocal() {
        super.postConstructLocal()
        runFlowDaoImpl = RunFlowDaoImpl(jdbcTemplate)
    }

    @Test
    fun createRunnableFlow() {
        val createRunnableFlow = runFlowDaoImpl.createRunnableFlow(FlowType.FLOW_3.flowName, "CONTEXT->3")
        Assert.assertNotNull(createRunnableFlow)
    }
}