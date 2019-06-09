package ru.vood.joinpoint2.infrastructure.flow

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.vood.joinpoint2.infrastructure.flow.data.DictionaryJoinPointData

@Service
@Transactional
open class DictionaryJoinPointOrderRunDao(private val jdbcTemplate: JdbcTemplate) : DictionaryJoinPointOrderRunDaoService {

    val rowMapperDictionaryJoinPointData = DictionaryJoinPointDataRowMapper()

    override fun nextJoinPoints(joinPoint: String, flowType: String): Map<String, DictionaryJoinPointData> {
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

    override fun prevJoinPoints(joinPoint: String, flowType: String): Map<String, DictionaryJoinPointData> {
        return jdbcTemplate.query(
                """select lv, cycl, flow, runner_jp, is_async_run, runnable_jp, synthetic_id, parent, run_bean, run_bean_in_ctx_type, run_bean_ret_ctx_type, run_bean_timeout, rbl_bean, rbl_bean_in_ctx_type, rbl_bean_ret_ctx_type, rbl_bean_timeout
                            from dict_act_ordered_jp_vw jp
                            where jp.flow=:1 and jp.runner_jp is not null and runnable_jp=:2""",
                rowMapperDictionaryJoinPointData,
                flowType, joinPoint
        ).asSequence()
                .map { Pair<String, DictionaryJoinPointData>(it.runner.toString(), it) }
                .toMap()
    }

    override fun getFirstJoinPoint(flowType: String): Map<String, DictionaryJoinPointData> {
        val listJP = jdbcTemplate.query(
                """select lv, cycl, flow, runner_jp, is_async_run, runnable_jp, synthetic_id, parent, run_bean, run_bean_in_ctx_type, run_bean_ret_ctx_type, run_bean_timeout, rbl_bean, rbl_bean_in_ctx_type, rbl_bean_ret_ctx_type, rbl_bean_timeout
                            from dict_act_ordered_jp_vw jp
                            where jp.flow=:1 and jp.parent='~'""",
                rowMapperDictionaryJoinPointData,
                flowType
        )
        return listJP.asSequence()
                .map { Pair<String, DictionaryJoinPointData>(it.runnable, it) }
                .toMap()
    }

}