package ru.vood.spring.integration.service

import ru.vood.spring.integration.entity.VBdObjectTypeEntity

interface VBdObjectTypeEntityService {
    fun findByCode(code: String): VBdObjectTypeEntity

    fun findCodeLike(code: String): List<VBdObjectTypeEntity>
}