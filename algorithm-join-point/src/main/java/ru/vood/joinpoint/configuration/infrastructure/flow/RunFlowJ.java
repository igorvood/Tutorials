package ru.vood.joinpoint.configuration.infrastructure.flow;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class RunFlowJ implements FlowService {
    private final JdbcTemplate jdbcTemplate;

    public RunFlowJ(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void runFlow(@NotNull FlowType ft) {
        jdbcTemplate.query("select * from ACT_ORDER_JOIN_POINT_VW",
                new RowMapper<Object>() {
                    @Override
                    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                        HashMap<String, String> stringStringHashMap = new HashMap<>();
                        stringStringHashMap.put("1", "2");
                        return stringStringHashMap;
                    }
                });

        jdbcTemplate.execute("begin :1:=RUN_FLOW.RUN(:2) end;", new CallableStatementCallback<Integer>() {
            @Override
            public Integer doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                cs.setString(2, ft.name());
                cs.execute();
                return cs.getInt(1);
            }
        });
    }
}
