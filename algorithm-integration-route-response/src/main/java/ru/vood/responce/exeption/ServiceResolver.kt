package ru.vood.responce.exeption

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.stereotype.Service
import ru.vood.responce.handler.ServiceName
import ru.vood.responce.handler.ServiceName.Companion.getByServiceName


@Service
class ServiceResolver(val jdbcTemplate: JdbcTemplate) : ServiceResolverService {

    override fun getServiceById(s: String): Pair<ServiceName, ServiceCodeExceptionMap> {
        val query = jdbcTemplate.query(
                """select mcs.id, mcse.exception_class, mcse.code, mcse.default_code
                        from jp.meta_consumer_service mcs
                            left join meta_cns_service_exception mcse on mcse.service_id = mcs.id
                        where mcs.ID='service 1' """,
                ResultSetExtractor<Map<ServiceName, ServiceCodeExceptionMap>> {
                    val res = HashMap<ServiceName, ServiceCodeExceptionMap>()
                    while (it.next()) {
                        val get = res[getByServiceName(it.getString(1))]
                        if (get != null) {
                            get[it.getString(2)] = it.getString(3)
                        } else {
                            val except = ServiceCodeExceptionMap()
                            except[it.getString(2)] = it.getString(3)
                            res[getByServiceName(it.getString(1))] = except
                        }
                    }
                    return@ResultSetExtractor res
                }
        )
        if (query.size != 1) throw IllegalStateException("Uupossible exception when try to find service ")
        val first = query.keys.first()
        val second = query[first]
        val pair = second?.let { Pair<ServiceName, ServiceCodeExceptionMap>(first, it) }
        return pair!!
    }
}