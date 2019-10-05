package ru.vood.test.db;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class ProcedureScriptPartExecutor implements ScriptPartExecutor {

    private static final Logger logger = LoggerFactory.getLogger(ProcedureScriptPartExecutor.class);

    private final JdbcTemplate jdbcTemplate;
    private final String executionString;

    public ProcedureScriptPartExecutor(JdbcTemplate jdbcTemplate, String procedureName) {
        Assert.assertNotNull("Parameter procedureName can't be null", procedureName);
        Assert.assertNotNull("Parameter jdbcTemplate can't be null", jdbcTemplate);
        this.jdbcTemplate = jdbcTemplate;
        this.executionString = String.format("begin %s(:1); end;", procedureName);
    }

    @Override
    public void execute(final String part) {
        //Инструкцию rename необходимо выполнять напрямую, без использования доп процедуры,
        //т.к. в инструкции rename нельзя указать схему таблицы
        final String nameExclusionStatement = "rename";
        if (part.toLowerCase().contains(nameExclusionStatement)) {
            logger.info("Statement rename execution without procedure.");
            logger.debug("Rename statement: %s", part);
            jdbcTemplate.update(part);
        } else {
            jdbcTemplate.execute(new CallableStatementCreator() {
                @Override
                public synchronized CallableStatement createCallableStatement(Connection con) throws SQLException {
                    return con.prepareCall(executionString);
                }
            }, (CallableStatementCallback<Void>) cs -> {
                cs.setString(1, part);
                cs.execute();
                return null;
            });
        }
    }
}
