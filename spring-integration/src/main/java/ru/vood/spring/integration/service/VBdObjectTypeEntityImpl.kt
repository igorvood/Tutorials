package ru.vood.spring.integration.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.vood.spring.integration.repository.VBdObjectTypeEntityRepository

@Service
@Transactional
open class VBdObjectTypeEntityImpl(val repository: VBdObjectTypeEntityRepository) : VBdObjectTypeEntityService {


    override open fun findByCode(code: String) = repository.findByCode(code)

    override fun findCodeLike(code: String) = repository.findByCodeLike(code)
}