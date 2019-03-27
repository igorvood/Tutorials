package ru.vood.spring.integration.service

import ru.vood.spring.integration.repository.VBdObjectTypeEntityRepository

//@Service
//@Transactional
open class VBdObjectTypeEntityImpl(val repository: VBdObjectTypeEntityRepository) : VBdObjectTypeEntityService {


    override fun findByCode(code: String) = repository.findByCode(code)

    override fun findCodeLike(code: String) = repository.findByCodeLike(code)
}