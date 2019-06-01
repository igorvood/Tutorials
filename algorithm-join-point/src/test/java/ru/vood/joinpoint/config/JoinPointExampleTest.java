package ru.vood.joinpoint.config;

import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;
import ru.vood.test.db.DataSourceTestUtil;

@Transactional
public class JoinPointExampleTest extends AbstractJoinPointDataSourceTest {

    @Override
    protected boolean isThisTestStdSqlResources() {
        System.out.println(DataSourceTestUtil.class);
        return true;
    }

    @Test
    public void test1() {

        System.out.println(jdbcTemplate);
        System.out.println(transactionManager);
    }
}
