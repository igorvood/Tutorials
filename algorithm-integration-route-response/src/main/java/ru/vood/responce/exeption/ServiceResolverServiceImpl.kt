package ru.vood.responce.exeption

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.stereotype.Service
import ru.vood.responce.handler.ServiceName
import ru.vood.responce.handler.getByServiceName

@Service
class ServiceResolverServiceImpl(val jdbcTemplate: JdbcTemplate) : ServiceResolverService {

    override fun getServiceById(s: String): Map<ServiceName, ServiceExceptionMap> {
        val query = jdbcTemplate.query(
                """select mcs.id, mcse.exception_class, mcse.code
                        from jp.meta_consumer_service mcs
                            left join meta_cns_service_exception mcse on mcse.service_id = mcs.id
                        where mcs.ID='service 1' """,
                ResultSetExtractor<Map<ServiceName, ServiceExceptionMap>> {
                    val res = HashMap<ServiceName, ServiceExceptionMap>()
                    while (it.next()) {
                        val get = res[getByServiceName(it.getString(1))]
                        if (get != null) {
                            get[it.getString(2)] = it.getString(3)
                        } else {
                            val except = ServiceExceptionMap()
                            except.put(it.getString(2), it.getString(3))
                            res.put(getByServiceName(it.getString(1)), except)
                        }
                    }
                    return@ResultSetExtractor res
                }
        )
        return query
    }
}