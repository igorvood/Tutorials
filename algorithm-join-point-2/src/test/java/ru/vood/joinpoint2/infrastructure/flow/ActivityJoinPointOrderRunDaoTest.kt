package ru.vood.joinpoint2.infrastructure.flow

import org.junit.Assert
import org.junit.Test
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.jdbc.core.RowMapper
import ru.vood.joinpoint2.config.AbstractJoinPointDataSourceTest
import ru.vood.joinpoint2.infrastructure.run.RunFlowDao
import ru.vood.joinpoint2.infrastructure.run.RunFlowDaoImpl

class ActivityJoinPointOrderRunDaoTest : AbstractJoinPointDataSourceTest() {

    lateinit var activityJoinPointOrderRunDao: ActivityJoinPointOrderRunDao
    lateinit var runFlowDaoImpl: RunFlowDao
    var idFlow: Long = 0

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
        val prevJoinPoints = activityJoinPointOrderRunDao.nextJoinPoints(idFlow, "join point 4")
        Assert.assertEquals(2, prevJoinPoints.size)
        Assert.assertTrue(prevJoinPoints.keys.contains("join point 5"))
        Assert.assertTrue(prevJoinPoints.keys.contains("join point 6"))
        Assert.assertEquals(idFlow, prevJoinPoints["join point 6"]!!.id)
    }

    @Test
    fun prevJoinPoints() {
        runFlowDaoImpl.createRunnableFlow(FLOW_TYPE_3, "Context - 33")
        idFlow = runFlowDaoImpl.createRunnableFlow(FLOW_TYPE_3, "Context - 33")
        val prevJoinPoints = activityJoinPointOrderRunDao.prevJoinPoints(idFlow, "join point 4")
        Assert.assertEquals(2, prevJoinPoints.size)
        Assert.assertTrue(prevJoinPoints.keys.contains("join point 3"))
        Assert.assertTrue(prevJoinPoints.keys.contains("join point 2"))
        Assert.assertEquals(idFlow, prevJoinPoints["join point 2"]!!.id)
    }

    @Test
//    @Ignore
    fun getFirstJoinPoint() {
        val inCtx = "Context - 33"
        idFlow = runFlowDaoImpl.createRunnableFlow(FLOW_TYPE_3, inCtx)
        val firstJoinPoint = activityJoinPointOrderRunDao.getFirstJoinPoint(idFlow, FLOW_TYPE_3)
        jdbcTemplate.execute("commit")
        Assert.assertEquals("join point 1", firstJoinPoint.joinPoint)
        Assert.assertEquals(inCtx, firstJoinPoint.runContext)

    }

    @Test
    fun getJoinPoint() {
//        for (i in 1..1000) {
//            runFlowDaoImpl.createRunnableFlow(FLOW_TYPE_3, "Context - 33")
//        }
        val inCtx = "Context - 33"
        idFlow = runFlowDaoImpl.createRunnableFlow(FLOW_TYPE_3, inCtx)
        val firstJoinPoint = activityJoinPointOrderRunDao.getJoinPoint(idFlow, "join point 4")
        Assert.assertEquals("join point 4", firstJoinPoint.joinPoint)
        val firstJoinPoint1 = activityJoinPointOrderRunDao.getJoinPoint(idFlow, "join point 1")
        Assert.assertEquals("join point 1", firstJoinPoint1.joinPoint)
        Assert.assertEquals(inCtx, firstJoinPoint1.runContext)

    }

    @Test(expected = DataIntegrityViolationException::class)
    fun insertReturnContextError() {
        val createRunnableFlow = runFlowDaoImpl.createRunnableFlow(FLOW_TYPE_3, "Context - 33")
        val joinPoint = "join point 4"
        val ctx = "context join point 4"
        activityJoinPointOrderRunDao.insertContext(createRunnableFlow, joinPoint, KindContext.RETURN, ctx)
        val contextData = getContext(createRunnableFlow, joinPoint)
        Assert.assertFalse(contextData.isEmpty())
        Assert.assertEquals(1, contextData.size)
        Assert.assertEquals(ctx, contextData[0].returnContext)
    }

    @Test(expected = DataIntegrityViolationException::class)
    fun insertReturnContextNoJpError() {
        val joinPoint = "join point 4"
        val ctx = "context join point 4"
        activityJoinPointOrderRunDao.insertContext(-9999, joinPoint, KindContext.RETURN, ctx)
    }

    @Test(expected = DataIntegrityViolationException::class)
    fun insertRunContextNoJpError() {
        val joinPoint = "join point 4"
        val ctx = "context join point 4"
        activityJoinPointOrderRunDao.insertContext(-9999, joinPoint, KindContext.RUN, ctx)
    }

    fun insertReturnContext() {
        val createRunnableFlow = runFlowDaoImpl.createRunnableFlow(FLOW_TYPE_3, "Context - 33")
        val joinPoint = "join point 4"
        val ctx = "context join point 4"
        activityJoinPointOrderRunDao.insertContext(createRunnableFlow, joinPoint, KindContext.RUN, ctx)
        activityJoinPointOrderRunDao.insertContext(createRunnableFlow, joinPoint, KindContext.RETURN, ctx)
        val contextData = getContext(createRunnableFlow, joinPoint)
        Assert.assertFalse(contextData.isEmpty())
        Assert.assertEquals(1, contextData.size)
        Assert.assertEquals(ctx, contextData[0].returnContext)
    }


    @Test
    fun insertRunContext() {
        val createRunnableFlow = runFlowDaoImpl.createRunnableFlow(FLOW_TYPE_3, "Context - 33")
        val joinPoint = "join point 4"
        val ctx = "context join point 4"
        activityJoinPointOrderRunDao.insertContext(createRunnableFlow, joinPoint, KindContext.RUN, ctx)
        val contextData = getContext(createRunnableFlow, joinPoint)
        Assert.assertFalse(contextData.isEmpty())
        Assert.assertEquals(1, contextData.size)
        Assert.assertEquals(ctx, contextData[0].runContext)
    }

    @Test
    fun setJoinPointBegin() {
        val flowId = runFlowDaoImpl.createRunnableFlow(FLOW_TYPE_3, "Context - 33")
        val joinPoint = "join point 4"
        var contextData = activityJoinPointOrderRunDao.getJoinPoint(flowId, joinPoint)
        Assert.assertEquals(JoinPointState.WAIT_RUNNING.stateName, contextData.state)
        activityJoinPointOrderRunDao.setJoinPointBegin(flowId, joinPoint)
        contextData = activityJoinPointOrderRunDao.getJoinPoint(flowId, joinPoint)
        Assert.assertEquals(JoinPointState.RUNNING.stateName, contextData.state)

        Assert.assertNotNull(contextData.dateBegin)
        Assert.assertNull(contextData.dateEnd)
    }

    @Test(expected = DataIntegrityViolationException::class)
    fun setJoinPointEndNoDateBegError() {
        val flowId = runFlowDaoImpl.createRunnableFlow(FLOW_TYPE_3, "Context - 33")
        val joinPoint = "join point 4"
        activityJoinPointOrderRunDao.setJoinPointEnd(flowId, joinPoint)
    }

    @Test
    fun setJoinPointEnd() {
        val flowId = runFlowDaoImpl.createRunnableFlow(FLOW_TYPE_3, "Context - 33")
        val joinPoint = "join point 4"
        activityJoinPointOrderRunDao.setJoinPointBegin(flowId, joinPoint)
        activityJoinPointOrderRunDao.setJoinPointEnd(flowId, joinPoint)

        val contextData = activityJoinPointOrderRunDao.getJoinPoint(flowId, joinPoint)

        Assert.assertNotNull(contextData.dateBegin)
        Assert.assertNotNull(contextData.dateEnd)
        Assert.assertEquals(JoinPointState.CLOSE.stateName, contextData.state)
    }


    private fun getContext(idFlow: Long, joinPoint: String): List<JPContextData> {
        return jdbcTemplate.query("select id, join_point, bean_id, run_context, return_context from act_join_point_context c where c.id=:1 and c.join_point=:2",
                RowMapper<JPContextData> { rs, rowNum ->
                    JPContextData(
                            rs.getLong(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5)
                    )
                },
                idFlow, joinPoint)
    }
}

data class JPContextData(
        val id: Long,
        val joinPoint: String,
        val beanId: String,
        val runContext: String?,
        val returnContext: String?
)


