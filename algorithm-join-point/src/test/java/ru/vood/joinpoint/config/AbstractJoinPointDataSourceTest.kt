package ru.vood.joinpoint.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.ContextHierarchy
import org.springframework.transaction.PlatformTransactionManager
import ru.vood.test.db.AbstractSmartDatasourceTests


@ContextHierarchy(
        ContextConfiguration(classes = [ConfigurationJointPointDatabaseTest::class]),
        ContextConfiguration(locations = ["classpath:tst-ctx-datasource-standard.xml"])
)
open class AbstractJoinPointDataSourceTest :
        AbstractSmartDatasourceTests() {

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate
    @Autowired
    lateinit var transactionManager: PlatformTransactionManager

    override fun postConstructLocal() {
        this.procedureName = "jp.test_util.exec_immediate"
        super.postConstructLocal()
    }
}