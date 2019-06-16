package ru.vood.responce.common

enum class MetaService(val serviceName: String) {

    SERVICE1("service 1"),
    SERVICE2("service 2");

    companion object {
        fun getByServiceName(name: String): MetaService {
            val toList = values().asSequence()
                    .filter { n -> n.serviceName == name }
                    .toList()
            if (toList.size != 1) {
                return toList[0]
            }
            throw RuntimeException("Service with name '$name' does not exists")
        }
    }
}

