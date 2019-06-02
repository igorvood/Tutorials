package ru.vood.joinpoint.config

import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.ContextHierarchy
import ru.vood.test.db.AbstractSmartDatasourceTests
import ru.vood.test.db.DataBaseConfiguration


@ContextHierarchy(
        ContextConfiguration(classes = [ConfigurationJointPointDatabaseTest::class]),
        ContextConfiguration(classes = [DataBaseConfiguration::class])

        //ContextConfiguration(locations = ["classpath:tst-ctx-datasource-standard.xml"])
)
open class AbstractJoinPointDataSourceTest :
        AbstractSmartDatasourceTests() {

    override fun postConstructLocal() {
        this.procedureName = "jp.test_util.exec_immediate"
        super.postConstructLocal()
    }
}