package ru.vood.joinpoint2.infrastructure.flow

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.vood.joinpoint2.infrastructure.flow.data.JoinPointData
import ru.vood.joinpoint2.infrastructure.flow.mapper.JoinPointDataRowMapper

@Service
@Transactional
open class ActivityJoinPointOrderRunDao(private val jdbcTemplate: JdbcTemplate) : ActivityJoinPointOrderRunDaoService {

    val rowMapperJoinPointData = JoinPointDataRowMapper()

    override fun nextJoinPoints(id: Long, joinPoint: String): Map<String, JoinPointData> {
        return jdbcTemplate.query(
                """select lv, cycl, flow, runner_jp, is_async_run, runnable_jp, synthetic_id, parent, run_bean, run_bean_in_ctx_type, run_bean_ret_ctx_type, run_bean_timeout, rbl_bean, rbl_bean_in_ctx_type, rbl_bean_ret_ctx_type, rbl_bean_timeout, runner_id, runner_run_context, runner_ret_context, runnable_run_context, runnable_ret_context
                            from act_ordered_jp_vw jp
                            where jp.runner_jp=:1 and jp.runner_id=:2""",
                rowMapperJoinPointData,
                joinPoint, id
        ).asSequence()
                .map { Pair<String, JoinPointData>(it.runnable, it) }
                .toMap()
    }

    override fun prevJoinPoints(id: Long, joinPoint: String): Map<String, JoinPointData> {
        return jdbcTemplate.query(
                """select lv, cycl, flow, runner_jp, is_async_run, runnable_jp, synthetic_id, parent, run_bean, run_bean_in_ctx_type, run_bean_ret_ctx_type, run_bean_timeout, rbl_bean, rbl_bean_in_ctx_type, rbl_bean_ret_ctx_type, rbl_bean_timeout, runner_id, runner_run_context, runner_ret_context, runnable_run_context, runnable_ret_context
                            from act_ordered_jp_vw jp
                            where jp.runner_jp is not null and jp.runnable_jp=:1 and jp.runner_id=:2""",
                rowMapperJoinPointData,
                joinPoint, id
        ).asSequence()
                .map { Pair<String, JoinPointData>(it.runner.toString(), it) }
                .toMap()
    }

    override fun getFirstJoinPoint(id: Long, flowType: String): Map<String, JoinPointData> {
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

    override fun getJoinPoint(id: Long, joinPoint: String): JoinPointData {
        return jdbcTemplate.queryForObject(
                """select distinct lv, cycl, flow, runner_jp, is_async_run, runnable_jp, synthetic_id, parent, run_bean, run_bean_in_ctx_type, run_bean_ret_ctx_type, run_bean_timeout, rbl_bean, rbl_bean_in_ctx_type, rbl_bean_ret_ctx_type, rbl_bean_timeout, runner_id, runner_run_context, runner_ret_context, runnable_run_context, runnable_ret_context
                            from act_ordered_jp_vw jp
                            where jp.runnable_jp=:1 and jp.runner_id=:2 and rownum=1""",
                rowMapperJoinPointData,
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