package ru.vood.joinpoint2.config

import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.ContextHierarchy
import ru.vood.test.db.AbstractSmartDatasourceTests
import ru.vood.test.db.DataBaseConfiguration


@ContextHierarchy(
        ContextConfiguration(classes = [ConfigurationJointPointDatabaseTest::class]),
        ContextConfiguration(classes = [DataBaseConfiguration::class])
)
abstract class AbstractJoinPointDataSourceTest :
        AbstractSmartDatasourceTests() {

    override fun postConstructLocal() {
        this.procedureName = "jp.test_util.exec_immediate"
        super.postConstructLocal()
    }
}