package ru.vood.joinpoint2.infrastructure.run

import oracle.jdbc.OracleTypes
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.CallableStatementCallback
import org.springframework.jdbc.core.CallableStatementCreator
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional()//(propagation = Propagation.REQUIRES_NEW)
open class RunFlowDaoImpl(private val jdbcTemplate: JdbcTemplate) : RunFlowDao {
    private val logger = LoggerFactory.getLogger(RunFlowDaoImpl::class.java)


    override fun createRunnableFlow(ft: String, inCtx: String): Long {
        logger.debug("call run.create_runnable_flow input flow type -> $ft, inputCtx ${inCtx.substring(1, 10)}")
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
                CallableStatementCallback<Long> { cs ->
                    cs.execute()
                    cs.getLong(1)
                }
        )
        logger.debug("End call run.create_runnable_flow output flow id  -> $runnableFlowId")
        return runnableFlowId
    }
}