package ru.vood.joinpoint2.infrastructure.flow

import org.junit.Assert
import org.junit.Test
import ru.vood.joinpoint2.config.AbstractJoinPointDataSourceTest
import ru.vood.joinpoint2.infrastructure.run.RunFlowDao
import ru.vood.joinpoint2.infrastructure.run.RunFlowDaoImpl

class ActivityJoinPointOrderRunDaoTest : AbstractJoinPointDataSourceTest() {

    lateinit var activityJoinPointOrderRunDao: ActivityJoinPointOrderRunDao
    lateinit var runFlowDaoImpl: RunFlowDao
    var idFlow: Int = 0

    val FLOW_TYPE_3 = FlowType.FLOW_3.flowName

    override fun postConstructLocal() {
        super.postConstructLocal()
        activityJoinPointOrderRunDao = ActivityJoinPointOrderRunDao(jdbcTemplate)
        runFlowDaoImpl = RunFlowDaoImpl(jdbcTemplate)
    }

    override fun isThisTestStdSqlResources(): Boolean {
        return true
    }

    @Test
    fun nextJoinPoints() {
        idFlow = runFlowDaoImpl.createRunnableFlow(FLOW_TYPE_3, "Context - 33")
        val prevJoinPoints = activityJoinPointOrderRunDao.nextJoinPoints(idFlow, "join point 4", FLOW_TYPE_3)
        Assert.assertEquals(2, prevJoinPoints.size)
        Assert.assertTrue(prevJoinPoints.keys.contains("join point 5"))
        Assert.assertTrue(prevJoinPoints.keys.contains("join point 6"))
    }

    @Test
    fun prevJoinPoints() {
        idFlow = runFlowDaoImpl.createRunnableFlow(FLOW_TYPE_3, "Context - 33")
        val prevJoinPoints = activityJoinPointOrderRunDao.prevJoinPoints(idFlow, "join point 4", FLOW_TYPE_3)
        Assert.assertEquals(2, prevJoinPoints.size)
        Assert.assertTrue(prevJoinPoints.keys.contains("join point 3"))
        Assert.assertTrue(prevJoinPoints.keys.contains("join point 2"))
    }

    @Test
    fun getFirstJoinPoint() {
        idFlow = runFlowDaoImpl.createRunnableFlow(FLOW_TYPE_3, "Context - 33")
        val firstJoinPoint = activityJoinPointOrderRunDao.getFirstJoinPoint(idFlow, FLOW_TYPE_3)
        jdbcTemplate.execute("commit")
        Assert.assertEquals(1, firstJoinPoint.size)
        Assert.assertEquals("join point 1", firstJoinPoint.keys.first())
    }
}