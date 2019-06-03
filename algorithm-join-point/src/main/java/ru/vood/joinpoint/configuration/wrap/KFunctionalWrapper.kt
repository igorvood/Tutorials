package ru.vood.joinpoint.configuration.wrap

import io.vavr.Function3
import java.util.function.BiFunction
import java.util.function.Function
import java.util.function.Supplier

interface KFunctionalWrapper {
    fun <R> wrap(f1: Supplier<R>): R =
            try {
                getOk(f1.get())
            } catch (e: Exception) {
                getError(e)
            }


    fun <T1, R> wrap(f1: Function<T1, R>, t1: T1): R =
            try {
                getOk(f1.apply(t1))
            } catch (e: Exception) {
                getError(e)
            }


    fun <T1, T2, R> wrap(f2: BiFunction<T1, T2, R>, t1: T1, t2: T2): R =
            try {
                getOk(f2.apply(t1, t2))
            } catch (e: Exception) {
                getError(e)
            }

    fun <T1, T2, T3, R> wrap(f3: Function3<T1, T2, T3, R>, t1: T1, t2: T2, t3: T3): R =
            try {
                getOk(f3.apply(t1, t2, t3))
            } catch (e: Exception) {
                getError(e)
            }

    fun <R> getError(e: Exception): R = throw RuntimeException(e.message, e)

    fun <R> getOk(apply: R): R = apply

}