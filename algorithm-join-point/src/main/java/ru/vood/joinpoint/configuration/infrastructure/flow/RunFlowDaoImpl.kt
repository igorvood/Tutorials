package ru.vood.joinpoint.configuration.infrastructure.flow

import org.springframework.jdbc.core.CallableStatementCallback
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
open class RunFlowDaoImpl(private val jdbcTemplate: JdbcTemplate) : RunFlowDao {
    override fun runFlow(ft: FlowType): String {
        //jdbcTemplate.execute(PreparedStatementCreator {  })
        val execute = jdbcTemplate.execute(
                "begin :1:=RUN_FLOW.RUN(:2) end;",
                CallableStatementCallback { cs ->
                    cs.setString(2, ft.name)
                    cs.execute()
                    cs.getString(1)
                })
        return execute
    }
    /*
        val runData = jdbcTemplate.query(
                "select ord.LV, ord.PATH, ord.RUNNER,ord.RUNNER_FLOW, ord.RUNNABLE, ord.RUNNABLE_FLOW,ord.RUN_BEAN,ord.RBL_BEAN_IN_CTX,ord.RUN_BEAN_RET_CTX, ord.RUN_BEAN_TIMEOUT,ord.RBL_BEAN, ord.RBL_BEAN_IN_CTX, ord.RBL_BEAN_RET_CTX,ord.RBL_BEAN_TIMEOUT from ACT_ORDER_JOIN_POINT_VW ord")
        { rs, rowNum ->
            RunData(rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getString(8),
                    rs.getString(9),
                    rs.getLong(10),
                    rs.getString(11),
                    rs.getString(12),
                    rs.getString(13),
                    rs.getLong(14)
            )
        }
*/

}