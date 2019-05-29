package ru.vood.joinpoint.configuration.infrastructure.flow

import oracle.jdbc.OracleTypes
import org.springframework.jdbc.core.CallableStatementCallback
import org.springframework.jdbc.core.CallableStatementCreator
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
open class RunFlowDaoImpl(private val jdbcTemplate: JdbcTemplate) : RunFlowDao {

    override fun runFlow(ft: FlowType): String {
        val execute = jdbcTemplate.execute(
                CallableStatementCreator { conn ->
                    val cs = conn.prepareCall(
                            "begin :1 := run.run_flow(:2); end;"
                    )
                    cs.registerOutParameter(1, OracleTypes.VARCHAR)
                    cs.setString(2, ft.name)
                    cs
                },
                CallableStatementCallback<String> { cs ->
                    cs.execute()
                    cs.getString(1)
                }
        )
        return execute
    }

}