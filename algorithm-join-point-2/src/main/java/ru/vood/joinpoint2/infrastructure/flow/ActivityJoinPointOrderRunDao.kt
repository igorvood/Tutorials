package ru.vood.joinpoint2.infrastructure.flow

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import ru.vood.joinpoint2.infrastructure.flow.data.DictionaryJoinPointData
import ru.vood.joinpoint2.infrastructure.flow.mapper.DictionaryJoinPointDataRowMapper

@Service
class ActivityJoinPointOrderRunDao(private val jdbcTemplate: JdbcTemplate) : ActivityJoinPointOrderRunDaoService {

    val rowMapperDictionaryJoinPointData = DictionaryJoinPointDataRowMapper()

    override fun nextJoinPoints(id: Int, joinPoint: String, flowType: String): Map<String, DictionaryJoinPointData> {
        return jdbcTemplate.query(
                """select lv, cycl, flow, runner_jp, is_async_run, runnable_jp, synthetic_id, parent, run_bean, run_bean_in_ctx_type, run_bean_ret_ctx_type, run_bean_timeout, rbl_bean, rbl_bean_in_ctx_type, rbl_bean_ret_ctx_type, rbl_bean_timeout
                            from dict_act_ordered_jp_vw jp
                            where jp.flow=:1 and runner_jp=:2""",
                rowMapperDictionaryJoinPointData,
                flowType, joinPoint
        ).asSequence()
                .map { Pair<String, DictionaryJoinPointData>(it.runnable, it) }
                .toMap()
    }

    override fun prevJoinPoints(id: Int, joinPoint: String, flowType: String): Map<String, DictionaryJoinPointData> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getFirstJoinPoint(id: Int, flowType: String): Map<String, DictionaryJoinPointData> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}