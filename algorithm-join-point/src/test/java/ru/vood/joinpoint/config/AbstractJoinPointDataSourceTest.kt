package ru.vood.joinpoint.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import ru.vood.test.db.AbstractSmartDatasourceTests

@ContextConfiguration(classes = [ConfigurationJointPointDatabaseTest::class])
@EnableTransactionManagement
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