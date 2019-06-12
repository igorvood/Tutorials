package ru.vood.joinpoint2.infrastructure.flow

enum class JoinPointState(val stateName: String) {
    WAIT_RUNNING("WAIT_RUNNING"),
    RUNNING("RUNNING"),
    CLOSE("CLOSE"),
    ERROR("ERROR")
}
