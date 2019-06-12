package ru.vood.joinpoint2.infrastructure.bean

import com.google.gson.GsonBuilder
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import ru.vood.joinpoint2.config.AbstractJoinPointDataSourceTest
import ru.vood.joinpoint2.infrastructure.flow.ActivityJoinPointOrderRunDaoService
import ru.vood.joinpoint2.infrastructure.flow.context.Bean1RunContext
import ru.vood.joinpoint2.infrastructure.run.RunFlowDao
import ru.vood.joinpoint2.infrastructure.run.RunFlowDaoImpl

@RunWith(SpringRunner::class)
@ContextConfiguration(classes = [BeanRunnerTestConfiguration::class])
class BeanRunnerTest : AbstractJoinPointDataSourceTest() {

    @Autowired
    lateinit var beanRunner: BeanRunner

    @Autowired
    lateinit var activityJoinPointOrderRunDaoService: ActivityJoinPointOrderRunDaoService

    lateinit var runFlowDaoImpl: RunFlowDao

    val gson = GsonBuilder()
            .setPrettyPrinting()
            .create()

    val bean1RunContext = Bean1RunContext(12345)

    var runCtx = gson.toJson(bean1RunContext)

    override fun postConstructLocal() {
        super.postConstructLocal()
        runFlowDaoImpl = RunFlowDaoImpl(jdbcTemplate)
    }

    @Test
    fun run() {
        val ft = "FLOW 3"
        val flowId = runFlowDaoImpl.createRunnableFlow(ft, runCtx)
        Assert.assertNotNull(flowId)
        val firstJoinPoint = activityJoinPointOrderRunDaoService.getFirstJoinPoint(flowId, ft)
        beanRunner.run(flowId, firstJoinPoint.joinPoint)
    }

    @Test
    fun tryToRunNext() {
    }

}