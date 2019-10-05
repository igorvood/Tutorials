package ru.vood.test.db;

import oracle.jdbc.OracleTypes;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import static org.junit.Assert.assertTrue;
import static ru.vood.test.db.DataSourceTestUtil.*;


//@ContextConfiguration(
//        locations = {
//                "classpath:applicationContext-datasource-tx.xml",
//                "classpath:ru/vood/test/db/tst-ctx-datasource-standard.xml",
//        }
//)
@Service
@Transactional
public abstract class AbstractDatasourceTests {

    private static final Logger logger = LoggerFactory.getLogger(AbstractDatasourceTests.class);

    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    protected PlatformTransactionManager transactionManager;

    protected ScriptPartExecutor scriptPartExecutor;
    private Timestamp startDatabaseCurrentTimestamp;
    private boolean testPackageInstalled = false;

    public static Timestamp getDatabaseCurrentTimestamp(JdbcTemplate jdbcTemplate) {
        return jdbcTemplate.queryForObject("select current_timestamp from dual", Timestamp.class);
    }

    public void setScriptPartExecutor(ScriptPartExecutor scriptPartExecutor) {
        this.scriptPartExecutor = scriptPartExecutor;
    }

    @PostConstruct
    private void postConstruct() {
        this.startDatabaseCurrentTimestamp = getDatabaseCurrentTimestamp();
        if (this.scriptPartExecutor == null) {
            this.scriptPartExecutor = part -> jdbcTemplate.update(part);
        }
        postConstructLocal();
    }

    protected Timestamp getStartDatabaseCurrentTimestamp() {
        Assert.assertNotNull(startDatabaseCurrentTimestamp);
        return (Timestamp) startDatabaseCurrentTimestamp.clone();
    }

    protected Timestamp getDatabaseCurrentTimestamp() {
        return getDatabaseCurrentTimestamp(jdbcTemplate);
    }

    /**
     * Переопределять в наследниках для целей встраивания в PostConstruct
     */
    protected void postConstructLocal() {
    }

    protected TransactionTemplate getTransactionTemplate() {
        TransactionTemplate tt = new TransactionTemplate(transactionManager);
        tt.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        return tt;
    }

    /**
     * Если тест в своём пекете в ресурсах имеет стандартные sql-скрипты инициализации и очистки,
     * то нужно переписать этот метод в возвращающий true.
     *
     * @return Нужно ли накатывать стандартно названные скрипты инициализации и очистки.
     */
    protected boolean isThisTestStdSqlResources() {
        return false;
    }

    protected void cleanupInternal() throws Exception {

        String oracleOutput = readOracleOutput();
        if (oracleOutput != null) {
            System.out.println("Oracle's dbms_output is:");
            System.out.println(oracleOutput);
        }

        if (testPackageInstalled) {
            jdbcTemplate.update("drop package " + STD_SUPPORT_PACKAGE_NAME);
            testPackageInstalled = false;
        }
    }

    protected void callStoredCodeAssert(String procedureNameWithBindPlaceholders, Object... args) {
        assertTrue(testPackageInstalled);
        jdbcTemplate.update(
                "begin " + STD_SUPPORT_PACKAGE_NAME + "." + procedureNameWithBindPlaceholders + "; end;",
                args
        );
    }

    private void enableOracleOutput() {
        jdbcTemplate.execute("begin dbms_output.enable(1000000); end;");
    }

    private String readOracleOutput() {
        return jdbcTemplate.execute((CallableStatementCreator) conn -> {
            CallableStatement cs = conn.prepareCall(
                    "declare\n" +
                            "  l_lines dbmsoutput_linesarray;\n" +
                            "  l_lines_count integer := 1000000;\n" +
                            "begin\n" +
                            "  dbms_output.get_lines(l_lines, l_lines_count);\n" +
                            "  open :1 for\n" +
                            "    select t.column_value\n" +
                            "    from table(l_lines) t;\n" +
                            "end;");
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            return cs;
        }, cs -> {

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {
                StringBuilder sb = new StringBuilder();

                while (rs.next()) {
                    sb.append(rs.getString(1)).append("\n");
                }
                rs.close();

                return sb.length() <= 0 ? null : sb.toString();
            }
        });
    }

    protected void setup() throws Exception {
    }

    protected void cleanup() throws Exception {
    }

    @Before
    public void init() throws Exception {
        logger.debug("init() starting");
        cleanupInternal();
        cleanup();
        applyStdResourceTeardownSqlScript();
        enableOracleOutput();
        setup();
        applyStdResourceSetupSqlScript();
        logger.debug("init() completed");
    }

    @After
    public void teardown() throws Exception {
        logger.debug("teardown() starting");
        cleanupInternal();
        applyStdResourceTeardownSqlScript();
        cleanup();
        logger.debug("teardown() completed");
    }

    protected void applyScript(final String[] script) {
        getTransactionTemplate().execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                for (String part : script) {
                    applyScriptPart(part, scriptPartExecutor);
                }
            }
        });
    }

    protected String[] readUtf8SqlScript(String fileName) throws Exception {
        return DataSourceTestUtil.readUtf8SqlScript(getClass(), fileName);
    }

    protected boolean isTestResourceExists(String fileName) throws Exception {
        return DataSourceTestUtil.isTestResourceExists(getClass(), fileName);
    }

    protected void applyScriptFromUtf8ScriptFile(String fileName) throws Exception {
        applyScript(readUtf8SqlScript(fileName));
    }

    protected void setupTestPackage() {
        if (!this.testPackageInstalled) {
            applyStdResourceSqlScript(getClass(), STD_SUPPORT_PACKAGE_FILE_SUFFIX, scriptPartExecutor);
            this.testPackageInstalled = true;
        }
    }

    protected final void applyStdResourceTeardownSqlScript() throws Exception {
        if (isThisTestStdSqlResources()) {
            jdbcTemplate.execute("commit");
            applyStdResourceTeardownSqlScriptForce(getClass());
            jdbcTemplate.execute("commit");
        }
    }

    protected final void applyStdResourceTeardownSqlScriptForce(final Class<?> clazz) throws Exception {
        getTransactionTemplate().execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                applyStdResourceSqlScript(clazz, SQL_SCRIPT_TEARDOWN_STD_SUFFIX, scriptPartExecutor);
            }
        });
    }

    protected final void applySpecResourceSqlScriptForce(final Class<?> clazz, final String fileName) {
        getTransactionTemplate().execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                DataSourceTestUtil
                        .applyScriptFromUtf8ScriptFile(clazz, fileName, false, scriptPartExecutor);
            }
        });
    }

    protected final void applyStdResourceSetupSqlScript() throws Exception {
        if (isThisTestStdSqlResources()) {
            jdbcTemplate.execute("commit");
            applyStdResourceSetupSqlScriptForce(getClass());
            jdbcTemplate.execute("commit");
        }
    }

    protected final void applyStdResourceSetupSqlScriptForce(final Class<?> clazz) throws Exception {
        getTransactionTemplate().execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                applyStdResourceSqlScript(clazz, SQL_SCRIPT_SETUP_STD_SUFFIX, scriptPartExecutor);
            }
        });
    }

}
