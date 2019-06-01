package ru.vood.joinpoint.config

import oracle.jdbc.driver.OracleDriver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.SimpleDriverDataSource
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
open class ConfigurationJointPointDatabaseTest {

    @Bean
    open fun getDataSource(): DataSource {
        return SimpleDriverDataSource(
                OracleDriver(),
                "jdbc:oracle:thin:@localhost:1521/jp",
                "JP",
                "1"
        )
    }

    @Bean
    open fun getJdbcTemplate(dataSource: DataSource): JdbcTemplate {
        return JdbcTemplate(dataSource)
    }

    @Bean
    open fun getTransactionManager(dataSource: DataSource): PlatformTransactionManager {
        return DataSourceTransactionManager(dataSource)
    }
}