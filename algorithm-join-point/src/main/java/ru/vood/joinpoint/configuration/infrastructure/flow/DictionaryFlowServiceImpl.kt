package ru.vood.joinpoint.configuration.infrastructure.flow

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Service

@Service
class DictionaryFlowServiceImpl(private val jdbcTemplate: JdbcTemplate) : DictionaryFlowService {
    val rowMapper = RowMapper<DictionaryJoinPointData> { rs, rowNum ->
        DictionaryJoinPointData(
                rs.getInt(1),
                rs.getInt(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getInt(6),
                rs.getString(7),
                rs.getString(8),
                rs.getString(9),
                rs.getString(10),
                rs.getString(11),
                rs.getString(12),
                rs.getInt(13),
                rs.getString(14),
                rs.getString(15),
                rs.getString(16),
                rs.getInt(17)
        )
    }

    //    lv, cycl, path, flow, runner, is_async_run, runnable, id, parent, run_bean, run_bean_in_ctx, run_bean_ret_ctx, run_bean_timeout, rbl_bean, rbl_bean_in_ctx, rbl_bean_ret_ctx, rbl_bean_timeout
    override fun getFirstJoinPoint(flowType: FlowType): DictionaryJoinPointData {
        val queryForObject = jdbcTemplate.queryForObject(
                "select lv, cycl, path, flow, runner, is_async_run, runnable, id, parent, run_bean, run_bean_in_ctx, run_bean_ret_ctx, run_bean_timeout, rbl_bean, rbl_bean_in_ctx, rbl_bean_ret_ctx, rbl_bean_timeout " +
                        " from act_order_join_point_vw jp" +
                        " where jp.flow=:1 and jp.lv=1",
                rowMapper,
                flowType.name
        )
        return queryForObject
    }

    override fun getNextJoinPoint(flowType: FlowType, currentBean: String): Map<String, DictionaryJoinPointData> {
        val beanList = jdbcTemplate.query(
                "select lv, cycl, path, flow, runner, is_async_run, runnable, id, parent, run_bean, run_bean_in_ctx, run_bean_ret_ctx, run_bean_timeout, rbl_bean, rbl_bean_in_ctx, rbl_bean_ret_ctx, rbl_bean_timeout " +
                        " from act_order_join_point_vw jp" +
                        " where jp.flow=:1 and jp.RUNNER=:2",
                rowMapper,
                flowType.name, currentBean
        )

        val nextBeans = beanList.asSequence()
                .map { dd -> Pair<String, DictionaryJoinPointData>(dd.run_bean, dd) }
                .toMap()
        return nextBeans
    }
}