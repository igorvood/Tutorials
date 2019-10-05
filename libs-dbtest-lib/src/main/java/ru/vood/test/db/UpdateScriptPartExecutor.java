package ru.vood.test.db;

import org.springframework.jdbc.core.JdbcTemplate;

public class UpdateScriptPartExecutor implements ScriptPartExecutor {

    private final JdbcTemplate jdbcTemplate;

    private UpdateScriptPartExecutor(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static ScriptPartExecutor getExecutor(JdbcTemplate jdbcTemplate) {
        return new UpdateScriptPartExecutor(jdbcTemplate);
    }

    @Override
    public void execute(String part) {
        jdbcTemplate.update(part);
    }

}
