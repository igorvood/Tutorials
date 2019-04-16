package ru.vood.joinpoint.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.core.JdbcTemplate
import ru.vood.joinpoint.configuration.beanfactory.JointPointConfigurationBeanFactoryPostProcessor

//@Configuration
open class JoinPointConfiguration {

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    @Bean
    open fun getJointPointConfigurationBeanFactoryPostProcessor(): BeanFactoryPostProcessor {
        return JointPointConfigurationBeanFactoryPostProcessor(jdbcTemplate)
    }

}