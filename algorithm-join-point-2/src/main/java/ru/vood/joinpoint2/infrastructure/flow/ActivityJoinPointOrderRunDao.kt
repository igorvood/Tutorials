package ru.vood.joinpoint2.infrastructure.flow

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class ActivityJoinPointOrderRunDao(private val jdbcTemplate: JdbcTemplate) : ActivityJoinPointOrderRunDaoService {

    val rowMapperDictionaryJoinPointData = DictionaryJoinPointDataRowMapper()

    override fun nextJoinPoints(id: Int, joinPoint: String, flowType: String): Map<String, DictionaryJoinPointData> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun prevJoinPoints(id: Int, joinPoint: String, flowType: String): Map<String, DictionaryJoinPointData> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getFirstJoinPoint(id: Int, flowType: String): Map<String, DictionaryJoinPointData> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}