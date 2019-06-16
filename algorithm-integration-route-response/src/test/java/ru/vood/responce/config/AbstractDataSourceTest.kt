package ru.vood.responce.config

import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.ContextHierarchy
import ru.vood.test.db.AbstractSmartDatasourceTests
import ru.vood.test.db.DataBaseConfiguration


@ContextHierarchy(
        ContextConfiguration(classes = [ConfigurationDatabaseTest::class]),
        ContextConfiguration(classes = [DataBaseConfiguration::class])
)
abstract class AbstractDataSourceTest :
        AbstractSmartDatasourceTests() {

    override fun postConstructLocal() {
        this.procedureName = "jp.test_util.exec_immediate"
        super.postConstructLocal()
    }
}