package ru.vood.joinpoint.configuration.infrastructure.flow;

import oracle.jdbc.OracleTypes;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class RunFlowJ /*implements FlowService*/ {
    private final JdbcTemplate jdbcTemplate;

    public RunFlowJ(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //@Override
    public void runFlow(@NotNull FlowType ft) {

        String execute = jdbcTemplate.execute(
                new CallableStatementCreator() {
                    @Override
                    public CallableStatement createCallableStatement(Connection conn) throws SQLException {
                        CallableStatement cs = conn.prepareCall(
                                "begin :1 := package.function(:2); end;"
                        );
                        cs.registerOutParameter(1, OracleTypes.VARCHAR);
                        cs.setString(2, ft.name());
                        return cs;
                    }
                },
                new CallableStatementCallback<String>() {
                    @Override
                    public String doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                        cs.execute();

                        try (ResultSet ctxCursorRs = (ResultSet) cs.getObject(1)) {
                            if (!ctxCursorRs.next()) {
                                return null;
                            }
                            return ctxCursorRs.getString(1);
                        }
                    }
                }
        );



        jdbcTemplate.query("select * from ACT_ORDER_JOIN_POINT_VW",
                new RowMapper<Object>() {
                    @Override
                    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                        HashMap<String, String> stringStringHashMap = new HashMap<>();
                        stringStringHashMap.put("1", "2");
                        return stringStringHashMap;
                    }
                });

/*
        jdbcTemplate.execute("begin :1:=RUN_FLOW.RUN(:2) end;", new CallableStatementCallback<Integer>() {
            @Override
            public Integer doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                cs.setString(2, ft.name());
                cs.execute();
                return cs.getInt(1);
            }
        });
*/
    }
}
