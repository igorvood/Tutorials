package ru.vood.joinpoint.configuration.bpp

import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import ru.vood.joinpoint.configuration.infrastructure.jp.JoinPointService
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl

@Component
class JoinPointBeanPostProcessor(private val jdbcTemplate: JdbcTemplate) : BeanPostProcessor {

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        val query = jdbcTemplate.query("select 1 from dual") { rs, rowNum ->
            rs.getString(1)
        }
        println(query)
        if ("Bean1".equals(beanName)) {
            val find = bean.javaClass.genericInterfaces
                    .asSequence()
                    .find { type -> (type as ParameterizedTypeImpl).rawType == JoinPointService::class.java }
            println(find)

            val javaClass = bean.javaClass
            println(bean)
            println(beanName)
        }
        return bean
    }
}