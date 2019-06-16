package ru.vood.joinpoint.configuration.bpp

import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class JoinPointBeanPostProcessor(private val jdbcTemplate: JdbcTemplate) : BeanPostProcessor {

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
/*
        val query = jdbcTemplate.query("select 1 from dual") { rs, rowNum ->
            rs.getString(1)
        }
        println(query)
        if ("Bean1".equals(beanName)) {
            val find = bean.javaClass.genericInterfaces
                    .asSequence()
                    .find { type -> (type as ParameterizedTypeImpl).rawType == BeanInterface::class.java }
            val parameterizedTypeImpl = find as ParameterizedTypeImpl
            parameterizedTypeImpl.actualTypeArguments[1]
            println(find)

            val methods = bean.javaClass.methods
            println(methods)
            val beanExists = jdbcTemplate.query("select DAB.BEAN_ID from DICT_ACT_BEAN DAB where DAB.BEAN_ID=:1",
                    beanName) { rs, rowNum ->
                rs.getString(1)
            }


            if (beanExists.isEmpty())
                throw RuntimeException("Bean $beanName does not exists in table DICT_ACT_BEAN")
            val javaClass = bean.javaClass
            println(bean)
            println(beanName)
        }
*/
        return bean
    }
}