package ru.vood.joinpoint.configuration.infrastructure.flow

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import ru.vood.joinpoint.JoinPointApplication

@SpringBootTest(classes = [JoinPointApplication::class])
@RunWith(SpringJUnit4ClassRunner::class)
class RunFlowDaoImplTest {
    @Autowired
    lateinit var runFlowDao: RunFlowDao


    @Test
    fun runFlow() {
        val runFlow = runFlowDao.createRunnableFlow(FlowType.FLOW_1)
        Assert.assertNotNull(runFlow)
    }
}