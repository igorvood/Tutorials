package ru.vood.joinpoint2.bpp

import org.junit.Assert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import ru.vood.joinpoint2.infrastructure.bean.WorkerBeanInterface
import java.util.*
import javax.annotation.PostConstruct

@Component
class KCheckMeta @Autowired
constructor(beanMap: Map<String, WorkerBeanInterface<*, *>>, private val jdbcTemplate: JdbcTemplate) {
    private val beanMap: HashMap<String, WorkerBeanInterface<*, *>>

    init {
        this.beanMap = HashMap(beanMap)
    }

    @PostConstruct
    private fun check() {
        println(beanMap)
        allBeanExists(beanMap)
    }

    private fun allBeanExists(beans: HashMap<String, WorkerBeanInterface<*, *>>) {
        val query = jdbcTemplate.query(
                "select BEAN_ID from DICT_ACT_BEAN DAB"
        ) { rs, rowNum -> rs.getString(1) }.asSequence()
                .filter { s -> !beans.containsKey(s) }
                .toList()
        if (query.isNotEmpty())
            Assert.fail("Beans does not exists in context $query")
    }

}

