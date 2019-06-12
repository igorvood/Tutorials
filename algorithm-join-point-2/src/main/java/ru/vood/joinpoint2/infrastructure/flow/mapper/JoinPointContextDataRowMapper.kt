package ru.vood.joinpoint2.infrastructure.flow.mapper

import org.springframework.jdbc.core.RowMapper
import ru.vood.joinpoint2.infrastructure.flow.data.JoinPointContextData
import java.sql.ResultSet

class JoinPointContextDataRowMapper : RowMapper<JoinPointContextData> {
    override fun mapRow(rs: ResultSet, rowNum: Int): JoinPointContextData? {

        val timestamp3 = rs.getTimestamp(3)
        val timestamp6 = rs.getTimestamp(6)
        println(timestamp3)
        println(timestamp6)
        return JoinPointContextData(
                rs.getLong(1),
                rs.getString(2),
                rs.getTimestamp(3),
                rs.getTimestamp(4),
                rs.getTimestamp(5),
                rs.getTimestamp(6),
                rs.getString(7),
                rs.getBoolean(8),
                rs.getString(9),
                rs.getString(10),
                rs.getString(11),
                rs.getString(12),
                rs.getString(13),
                rs.getBoolean(14)
        )
    }
}