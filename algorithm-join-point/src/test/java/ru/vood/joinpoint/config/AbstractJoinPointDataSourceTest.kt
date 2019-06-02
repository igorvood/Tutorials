package ru.vood.joinpoint.config

import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.ContextHierarchy
import ru.vood.test.db.AbstractSmartDatasourceTests


@ContextHierarchy(
        ContextConfiguration(classes = [ConfigurationJointPointDatabaseTest::class]),
        ContextConfiguration(locations = ["classpath:tst-ctx-datasource-standard.xml"])
)
open class AbstractJoinPointDataSourceTest :
        AbstractSmartDatasourceTests() {

    override fun postConstructLocal() {
        this.procedureName = "jp.test_util.exec_immediate"
        super.postConstructLocal()
    }
}