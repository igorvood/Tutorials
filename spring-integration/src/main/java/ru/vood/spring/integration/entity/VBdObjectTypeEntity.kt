package ru.vood.spring.integration.entity

import java.math.BigDecimal

data class VBdObjectTypeEntity(
        var id: BigDecimal?,

        var code: String?,

        var name: String?) {
    constructor() : this(null, null, null)

}