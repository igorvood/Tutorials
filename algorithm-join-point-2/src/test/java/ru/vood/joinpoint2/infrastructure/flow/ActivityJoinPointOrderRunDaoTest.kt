package ru.vood.joinpoint2.infrastructure.flow

import org.junit.Test
import ru.vood.joinpoint2.config.AbstractJoinPointDataSourceTest

class ActivityJoinPointOrderRunDaoTest : AbstractJoinPointDataSourceTest() {

    lateinit var activityJoinPointOrderRunDao: ActivityJoinPointOrderRunDao
    val FLOW_TYPE_3 = FlowType.FLOW_3.flowName

    override fun postConstructLocal() {
        super.postConstructLocal()
        activityJoinPointOrderRunDao = ActivityJoinPointOrderRunDao(jdbcTemplate)
    }

    override fun isThisTestStdSqlResources(): Boolean {
        return true
    }

    @Test
    fun nextJoinPoints() {
    }

    @Test
    fun prevJoinPoints() {
    }

    @Test
    fun getFirstJoinPoint() {
    }
}