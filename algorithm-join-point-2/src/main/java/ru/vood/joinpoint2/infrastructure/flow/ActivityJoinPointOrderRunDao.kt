package ru.vood.joinpoint2.infrastructure.flow

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.vood.joinpoint2.infrastructure.flow.data.JoinPointContextData
import ru.vood.joinpoint2.infrastructure.flow.data.JoinPointData
import ru.vood.joinpoint2.infrastructure.flow.mapper.JoinPointContextDataRowMapper
import ru.vood.joinpoint2.infrastructure.flow.mapper.JoinPointDataRowMapper

@Service
@Transactional
open class ActivityJoinPointOrderRunDao(private val jdbcTemplate: JdbcTemplate) : ActivityJoinPointOrderRunDaoService {

    val rowMapperJoinPointData = JoinPointDataRowMapper()
    val joinPointContextDataRowMapper = JoinPointContextDataRowMapper()

    override fun nextJoinPoints(id: Long, joinPoint: String): Map<String, JoinPointContextData> {
        return jdbcTemplate.query(
                """select flow_id, join_point, expire_at, timout_detected_at, date_beg, date_end,state, is_closed, bean_name, run_context_id, return_context_id, run_context, return_context, is_async_run
                            from act_next_jp_info jp
                            where jp.runner_jp=:1 and jp.flow_id=:2""",
                joinPointContextDataRowMapper,
                joinPoint, id
        ).asSequence()
                .map { Pair<String, JoinPointContextData>(it.joinPoint, it) }
                .toMap()
    }

    override fun prevJoinPoints(id: Long, joinPoint: String): Map<String, JoinPointContextData> {
        return jdbcTemplate.query(
                """select flow_id, join_point, expire_at, timout_detected_at, date_beg,date_end, state, is_closed, bean_name, run_context_id, return_context_id, run_context, return_context, is_async_run
                            from act_prev_jp_info jp
                            where jp.runnable_jp=:1 and jp.flow_id=:2""",
                joinPointContextDataRowMapper,
                joinPoint, id
        ).asSequence()
                .map { Pair<String, JoinPointContextData>(it.joinPoint, it) }
                .toMap()
    }

    override fun getFirstJoinPoint(id: Long, flowType: String): Map<String, JoinPointData> {

        //flow_id, join_point, expire_at, timout_detected_at, date_beg, state, is_closed, bean_name, run_context_id, return_context_id, run_context, return_context
        return jdbcTemplate.query(
                """select lv, cycl, flow, runner_jp, is_async_run, runnable_jp, synthetic_id, parent, run_bean, run_bean_in_ctx_type, run_bean_ret_ctx_type, run_bean_timeout, rbl_bean, rbl_bean_in_ctx_type, rbl_bean_ret_ctx_type, rbl_bean_timeout, runner_id, runner_run_context, runner_ret_context, runnable_run_context, runnable_ret_context
                            from act_ordered_jp_vw jp
                            where jp.flow=:1 and jp.parent='~' and jp.runner_id=:2""",
                rowMapperJoinPointData,
                flowType, id
        ).asSequence()
                .map { Pair<String, JoinPointData>(it.runnable, it) }
                .toMap()
    }

    override fun getJoinPoint(id: Long, joinPoint: String): JoinPointContextData {
        return jdbcTemplate.queryForObject(
                """select distinct flow_id, join_point, expire_at, timout_detected_at, date_beg, date_end, state, is_closed, bean_name, run_context_id, return_context_id, run_context, return_context, is_async_run
                            from act_prev_jp_info jp
                            where jp.join_point=:1 and jp.flow_id=:2""",
                joinPointContextDataRowMapper,
                joinPoint, id
        )
    }

    override fun insertReturnContext(id: Long, joinPoint: String, ctx: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun insertRunContext(id: Long, joinPoint: String, ctx: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}