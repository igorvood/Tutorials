package ru.vood.spring.integration.commonExample.service

import ru.vood.spring.integration.commonExample.entity.VBdObjectTypeEntity

interface VBdObjectTypeEntityService {
    fun findByCode(code: String): VBdObjectTypeEntity

    fun findCodeLike(code: String): List<VBdObjectTypeEntity>
}