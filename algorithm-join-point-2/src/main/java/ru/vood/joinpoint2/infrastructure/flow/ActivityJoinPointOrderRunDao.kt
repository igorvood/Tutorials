package ru.vood.joinpoint2.infrastructure.flow

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.vood.joinpoint2.infrastructure.flow.data.JoinPointContextData
import ru.vood.joinpoint2.infrastructure.flow.mapper.JoinPointContextDataRowMapper

@Service
@Transactional
open class ActivityJoinPointOrderRunDao(private val jdbcTemplate: JdbcTemplate) : ActivityJoinPointOrderRunDaoService {

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

    override fun getFirstJoinPoint(id: Long, flowType: String): JoinPointContextData {
        val queryForObject = jdbcTemplate.queryForObject(
                """select flow_id, join_point, expire_at, timout_detected_at, date_beg,date_end, state, is_closed, bean_name, run_context_id, return_context_id, run_context, return_context, is_async_run
                            from act_first_jp_info jp
                            where jp.flow=:1 and jp.flow_id=:2""",
                joinPointContextDataRowMapper,
                flowType, id
        )
        return queryForObject
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

    override fun insertContext(id: Long, joinPoint: String, kindContext: KindContext, ctx: String) {
        jdbcTemplate.update(
                "begin run.insert_context(:1, :2, :3, :4); end;",
                id, joinPoint, kindContext.kind, ctx
        )
    }

}