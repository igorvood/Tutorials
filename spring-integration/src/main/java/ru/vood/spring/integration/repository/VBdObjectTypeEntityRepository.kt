package ru.vood.spring.integration.repository

import org.springframework.stereotype.Service
import ru.vood.spring.integration.entity.VBdObjectTypeEntity
import java.math.BigDecimal

@Service
class VBdObjectTypeEntityRepository {

    val typeEntity = arrayListOf<String>("Table", "View", "Tablespace")

    fun findByCode(code: String): VBdObjectTypeEntity {
        val find = typeEntity.asSequence().find { it == code }
        return VBdObjectTypeEntity(
                BigDecimal(find!!.hashCode()),
                find,
                find)
    }

    fun findByCodeLike(code: String): List<VBdObjectTypeEntity> {
        return typeEntity.asSequence()
                .filter { it.contains(code) }
                .map {
                    VBdObjectTypeEntity(
                            BigDecimal(it.hashCode()),
                            it,
                            it)
                }
                .toList()

    }

}
