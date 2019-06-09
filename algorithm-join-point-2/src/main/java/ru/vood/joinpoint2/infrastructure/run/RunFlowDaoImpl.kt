package ru.vood.joinpoint2.infrastructure.run

import oracle.jdbc.OracleTypes
import org.springframework.jdbc.core.CallableStatementCallback
import org.springframework.jdbc.core.CallableStatementCreator
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional()//(propagation = Propagation.REQUIRES_NEW)
open class RunFlowDaoImpl(private val jdbcTemplate: JdbcTemplate) : RunFlowDao {

    override fun createRunnableFlow(ft: String, inCtx: String): Int {
        val runnableFlowId = jdbcTemplate.execute(
                CallableStatementCreator { conn ->
                    val cs = conn.prepareCall(
                            "begin :1 := run.create_runnable_flow(:2, :3); end;"
                    )
                    cs.registerOutParameter(1, OracleTypes.VARCHAR)
                    cs.setString(2, ft)
                    cs.setString(3, inCtx)
                    cs
                },
                CallableStatementCallback<Int> { cs ->
                    cs.execute()
                    cs.getInt(1)
                }
        )
        return runnableFlowId

    }
}