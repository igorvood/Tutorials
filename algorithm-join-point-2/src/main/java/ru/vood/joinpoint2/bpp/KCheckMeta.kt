package ru.vood.joinpoint2.bpp

import org.junit.Assert
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import ru.vood.joinpoint2.infrastructure.bean.WorkerBeanInterface
import ru.vood.joinpoint2.infrastructure.run.RunFlowDaoImpl
import java.sql.ResultSet
import java.util.*
import javax.annotation.PostConstruct

@Component
class KCheckMeta @Autowired
constructor(beanMap: Map<String, WorkerBeanInterface<*, *>>, private val jdbcTemplate: JdbcTemplate) {

    private val logger = LoggerFactory.getLogger(RunFlowDaoImpl::class.java)
    private val beanMap: HashMap<String, WorkerBeanInterface<*, *>>

    init {
        this.beanMap = HashMap(beanMap)
    }

    @PostConstruct
    private fun checkMeta() {
        println(beanMap)
        allBeanExists(beanMap)
        allJoinPointUse()
        allContextСonsistency()
        beanContextСonsistencyMeta(beanMap)
    }

    private fun beanContextСonsistencyMeta(beanMap: HashMap<String, WorkerBeanInterface<*, *>>) {
        val metaBeanCtx = jdbcTemplate.query("select BEAN_ID, RUN_CONTEXT, RETURN_CONTEXT from DICT_ACT_BEAN",
                { rs: ResultSet, rowNum: Int -> Pair<String, Pair<String, String>>(rs.getString(1), Pair(rs.getString(2), rs.getString(3))) }
        ).toMap()

        val beanMap = beanMap.asSequence()
                .map { b -> Pair<String, Pair<String, String>>(b.key, getClassParameters(b.value.javaClass)) }
                .toMap()
        val toList = metaBeanCtx.asSequence()
                .filter { meta -> !beanMap.contains(meta.key) }

                //todo тут продолжить проверку консистетнтности контекста спринг и меты
                .filter { meta -> beanMap.get(meta.key).second.se }



        println(metaBeanCtx)
        println(beanMap)
    }

    private fun getClassParameters(clazz: Class<WorkerBeanInterface<*, *>>): Pair<String, String> {
        val methods = clazz.declaredMethods
        val methodsList = methods.asSequence()
                .filter { m -> m.name == "doIt" }
                .filter { m -> m.returnType != java.lang.Object::class.java }
                .filter { m -> m.genericParameterTypes.size == 1 }
                //.map { m-> Pair<Class<*>,Class<*>>(m.returnType, m.genericParameterTypes[0]) }
                .toList()
        if (methodsList.size != 1)
            throw RuntimeException("Class ${clazz.canonicalName} must have  only one method doIt with one parameter ")
        val method = methodsList[0]
        val type = method.genericParameterTypes[0]
        if (type != null)
            return Pair<String, String>(type.typeName, method.returnType.name)
        throw RuntimeException("Impossible Exception")
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

    private fun allJoinPointUse() {
        val query = jdbcTemplate.query(
                """select DAJP.ID
                        from DICT_ACT_JOIN_POINT DAJP
                                 left join DICT_ACT_RUN DARb on DARb.RUNNABLE_JP = DAJP.ID
                                 left join DICT_ACT_RUN DARu on DARu.RUNNER_JP = DAJP.ID
                        where DARb.RUNNABLE_JP is null
                          and DARu.RUNNER_JP is null"""
        ) { rs, rowNum -> rs.getString(1) }

        query.forEach { s -> logger.warn("Join point does not use $s") }
    }

    private fun allContextСonsistency() {
        val query = jdbcTemplate.query(
                """select distinct run.RUNNER_JP, run.RUNNABLE_JP
                        from DICT_ACT_RUN run
                                 join DICT_ACT_JOIN_POINT jp on jp.ID = run.RUNNABLE_JP
                                 join DICT_ACT_BEAN DAB on DAB.BEAN_ID = jp.BEAN_NAME

                                 join DICT_ACT_JOIN_POINT jpr on jpr.ID = run.RUNNER_JP
                                 join DICT_ACT_BEAN DABr on DABr.BEAN_ID = jpr.BEAN_NAME
                        where DABr.RETURN_CONTEXT != DAB.RUN_CONTEXT"""
        ) { rs, rowNum -> Pair<String, String>(rs.getString(1), rs.getString(2)) }

        query.forEach { s -> logger.error("Join point have not consistency context running $s") }

        if (query.isNotEmpty())
            Assert.fail("Join point have not consistency context running $query")

    }

}

