package ru.vood.joinpoint2.infrastructure.flow

import org.junit.Assert
import org.junit.Test
import ru.vood.joinpoint2.config.AbstractJoinPointDataSourceTest

class DictionaryJoinPointOrderRunDaoTest : AbstractJoinPointDataSourceTest() {

    lateinit var dictionaryJoinPointOrderRunDao: DictionaryJoinPointOrderRunDao
    val FLOW_TYPE_3 = FlowType.FLOW_3.flowName

    override fun postConstructLocal() {
        super.postConstructLocal()
        dictionaryJoinPointOrderRunDao = DictionaryJoinPointOrderRunDao(jdbcTemplate)
    }

    @Test
    fun nextJoinPoints() {
        val prevJoinPoints = dictionaryJoinPointOrderRunDao.nextJoinPoints("join point 4", FLOW_TYPE_3)
        Assert.assertEquals(2, prevJoinPoints.size)
        Assert.assertTrue(prevJoinPoints.keys.contains("join point 5"))
        Assert.assertTrue(prevJoinPoints.keys.contains("join point 6"))
    }

    @Test
    fun prevJoinPoints() {
        val prevJoinPoints = dictionaryJoinPointOrderRunDao.prevJoinPoints("join point 4", FLOW_TYPE_3)
        Assert.assertEquals(2, prevJoinPoints.size)
        Assert.assertTrue(prevJoinPoints.keys.contains("join point 3"))
        Assert.assertTrue(prevJoinPoints.keys.contains("join point 2"))
        val toList = prevJoinPoints.keys.toList()

//        val asList = asList("join point 2", "join point 3")
//        Assert.assertThat<List<String>>(toList, containsInAnyOrder(asList))
    }

    @Test
    fun getFirstJoinPoint() {
        val firstJoinPoint = dictionaryJoinPointOrderRunDao.getFirstJoinPoint(FLOW_TYPE_3)
        Assert.assertEquals(1, firstJoinPoint.size)
        Assert.assertEquals("join point 1", firstJoinPoint.keys.first())
    }
}