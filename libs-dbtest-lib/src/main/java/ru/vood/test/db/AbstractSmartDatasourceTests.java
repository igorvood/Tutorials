package ru.vood.test.db;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@RunWith(SpringJUnit4ClassRunner.class)
@Service
public class AbstractSmartDatasourceTests extends AbstractDatasourceTests {

    private final boolean withCheckMutatingTest = this.getClass().isAnnotationPresent(WithCheckMutatingTest.class)
            && this.getClass().getAnnotation(WithCheckMutatingTest.class).value();
    protected String procedureName;
    private Map<String, Long> tableStat;

    @Override
    protected void postConstructLocal() {
        this.scriptPartExecutor = new ProcedureScriptPartExecutor(jdbcTemplate, procedureName);
        if (withCheckMutatingTest) {
            tableStat = loadTableStat();
        }
    }

    @Override
    protected void cleanupInternal() throws Exception {
//        cleanAdmArtLog();
        super.cleanupInternal();
        checkTableStat();
    }

    //    private void cleanAdmArtLog() {
//        final Timestamp startTime = getStartDatabaseCurrentTimestamp();
//        cleanAdmArtLog(jdbcTemplate,startTime );
//    }
//
//    private void cleanAdmArtLog(JdbcTemplate jdbcTemplate, Timestamp startTime) {
//    }
    protected final Map<String, String> diffWithMapFromSelect(Map<String, ?> expected, String sql, Object... args) {
        Map<String, String> errors = new LinkedHashMap<>();
        Map<String, ?> sqlResult = jdbcTemplate.query(sql, args, rs -> {
            Map<String, Object> result = new HashMap<>();
            ResultSetMetaData metaData = rs.getMetaData();
            Assert.assertEquals("The Select must contains 2 columns in result", 2, metaData.getColumnCount());
            int valueType = metaData.getColumnType(2);
            while (rs.next()) {
                switch (valueType) {
                    case Types.CHAR:
                    case Types.VARCHAR:
                        result.put(rs.getString(1), rs.getString(2));
                        break;
                    case Types.NUMERIC:
                        result.put(rs.getString(1), rs.getLong(2));
                        break;
                    case Types.DECIMAL:
                        result.put(rs.getString(1), rs.getBigDecimal(2));
                        break;
                    case Types.TIMESTAMP:
                        result.put(rs.getString(1), rs.getTimestamp(2));
                        break;
                    default:
                }
            }
            return result;
        });
        Set<String> absentInResult = minus(expected.keySet(), sqlResult.keySet());
        if (!absentInResult.isEmpty()) {
            errors.put("absent entries", mapFromKeySet(expected, absentInResult).toString());
        }
        Set<String> redundantInResult = minus(sqlResult.keySet(), expected.keySet());
        if (!absentInResult.isEmpty()) {
            errors.put("redundant entries", mapFromKeySet(expected, absentInResult).toString());
        }
        Map<String, ?> diff = sqlResult.entrySet().stream()
                .filter(e -> !Objects.equals(e.getValue(), expected.get(e.getKey())))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
        if (!diff.isEmpty()) {
            errors.put("thera are difference in entries ", diff.toString());
        }
        return errors;
    }

    protected Set<String> querySetOfString(String select, Object... args) {
        return querySetOfString(select, 1, args);
    }

    protected Set<String> querySetOfString(String select, int stringColumnIndex, Object... args) {
        return jdbcTemplate.query(select, (ResultSet rs) -> {
            Set<String> res = new HashSet<>();
            while (rs.next()) {
                Assert.assertTrue(res.add(rs.getString(stringColumnIndex)));
            }
            return res;
        }, args);
    }

    private Set<String> minus(Set<String> set1, Set<String> set2) {
        return set1.stream()
                .filter(k -> !set2.contains(k))
                .collect(Collectors.toSet());
    }

    private Map<String, ?> mapFromKeySet(Map<String, ?> map, Set<String> keySet) {
        return map.entrySet().stream()
                .filter(e -> keySet.contains(e.getKey()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private void checkTableStat() {
        if (tableStat == null) {
            return;
        }
        final Map<String, String> errors = new HashMap<>();

        final Map<String, Long> startTableStat = new HashMap<>(tableStat);
        final Map<String, Long> endTableStat = loadTableStat();

        final Map<String, Long> removedTbls = startTableStat.entrySet().stream()
                .filter(e -> !endTableStat.containsKey(e.getKey()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
        if (removedTbls.size() > 0) {
            errors.put("there are removed tables found", removedTbls.toString());
        }

        final Map<String, Long> appendedTbls = endTableStat.entrySet().stream()
                .filter(e -> !startTableStat.containsKey(e.getKey()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
        if (appendedTbls.size() > 0) {
            errors.put("there are new tables found", appendedTbls.toString());
        }

        final Map<String, Long> diffTbls = startTableStat.entrySet().stream()
                .filter(e -> endTableStat.containsKey(e.getKey()) &&
                        !Objects.equals(endTableStat.get(e.getKey()), e.getValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
        if (diffTbls.size() > 0) {
            errors.put(String.format("the test %s mutate data: there are difference between tables found", this.getClass().getSimpleName()), diffTbls.toString());
        }

        if (errors.size() > 0)
            throw new IllegalStateException(errors.toString());
    }

    private void gatherStats() {
        jdbcTemplate.update("begin dbms_stats.gather_schema_stats(ownname => 'JP', no_invalidate => true, fource => true); end;");
    }

    private Map<String, Long> loadTableStat() {
        gatherStats();
        return jdbcTemplate.query("select t.table_name, nvl(ts.num_rows,0) as rcnt\n" +
                "from USER_TABLES t\n" +
                "         join USER_TAB_STATISTICS ts on ts.table_name = t.table_name\n" +
                "where t.iot_name is null\n" +
                "and t.table_name not like 'BIN$%'\n" +
                "order by table_name", (ResultSet rs) -> {
            Map<String, Long> stat = new HashMap<>(300);
            while (rs.next()) {
                stat.put(rs.getString(1), rs.getLong(2));
            }
            return stat;
        });
    }

    protected void executeDDlScript(String resourceFileNameSuffix) {
        String[] script = DataSourceTestUtil.readUtf8SqlScript(
                this.getClass(),
                this.getClass().getSimpleName() + "." + resourceFileNameSuffix,
                false
        );
        DataSourceTestUtil.applyScript(script, new ProcedureScriptPartExecutor(jdbcTemplate, procedureName));
    }

}
