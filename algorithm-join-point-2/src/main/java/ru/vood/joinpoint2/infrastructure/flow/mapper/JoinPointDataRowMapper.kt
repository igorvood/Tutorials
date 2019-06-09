package ru.vood.joinpoint2.infrastructure.flow.mapper

import org.springframework.jdbc.core.RowMapper
import ru.vood.joinpoint2.infrastructure.flow.data.JoinPointData
import java.sql.ResultSet

class JoinPointDataRowMapper : RowMapper<JoinPointData> {
    override fun mapRow(rs: ResultSet, rowNum: Int): JoinPointData? {
        return JoinPointData(
                rs.getInt(1),
                rs.getInt(2),
                rs.getString(3),
                rs.getString(4),
                rs.getInt(5),
                rs.getString(6),
                rs.getString(7),
                rs.getString(8),
                rs.getString(9),
                rs.getString(10),
                rs.getString(11),
                rs.getInt(12),
                rs.getString(13),
                rs.getString(14),
                rs.getString(15),
                rs.getInt(16),
                rs.getLong(17),

                rs.getString(18),
                rs.getString(19),
                rs.getString(20),
                rs.getString(21)
        )
    }
}