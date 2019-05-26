package ru.vood.joinpoint.configuration.context

interface KContextType<T> {
    fun getCtx(): T
}
