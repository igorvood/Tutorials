package ru.vood.responce.handler

enum class ServiceName(val serviceName: String) {

    SERVICE1("service 1"),
    SERVICE2("service 2")
}

fun getByServiceName(name: String): ServiceName {
    val toList = ServiceName.values().asSequence()
            .filter { n -> n.serviceName == name }
            .toList()
    if (toList.size != 1) {
        return toList[0]
    }
    throw RuntimeException("Service with name '$name' does not exists")
}
