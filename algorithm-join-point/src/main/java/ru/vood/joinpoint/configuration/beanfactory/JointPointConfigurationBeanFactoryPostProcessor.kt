package ru.vood.joinpoint.configuration.beanfactory

import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.beans.factory.support.BeanDefinitionBuilder
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet


//@Component
class JointPointConfigurationBeanFactoryPostProcessor(private val jdbcTemplate: JdbcTemplate) : BeanFactoryPostProcessor, RowMapper<Pair<String, String>> {

    private val logger = LogFactory.getLog(JointPointConfigurationBeanFactoryPostProcessor::class.java)

    private lateinit var beanFactory: ConfigurableListableBeanFactory

    private lateinit var registry: BeanDefinitionRegistry


    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        if (beanFactory is BeanDefinitionRegistry) {
            this.beanFactory = beanFactory
            this.registry = beanFactory
            registerJoinPoint()
        } else if (logger.isWarnEnabled) {
            logger.warn("BeanFactory is not a BeanDefinitionRegistry. " + "The default Spring Integration infrastructure beans are not going to be registered")
        }
    }

    private fun registerJoinPoint() {
        val stateList = jdbcTemplate.query("select st.ID, st.DESCRIPTION from DICT_ACT_STATE st"
        ) { rs, rowNum -> Pair(rs.getString(1), rs.getString(1)) }

        stateList.asSequence().forEach {
            if (!this.beanFactory.containsBean(it.first)) {
                if (logger.isInfoEnabled) {
                    logger.info("No bean named '${it.first}' has been explicitly defined. Therefore, it will be created.")
                }
                val joinPoint = BeanDefinitionBuilder.genericBeanDefinition(JoinPointState::class.java)
                        .addPropertyValue("id", it.first)
                        .addPropertyValue("desc", it.second)
                        .beanDefinition
                this.registry.registerBeanDefinition(it.first, joinPoint)
            }
        }

    }

    override fun mapRow(rs: ResultSet, rowNum: Int): Pair<String, String>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}