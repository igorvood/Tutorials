package ru.vood.joinpoint.configuration.wrap;

import io.vavr.*;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public interface BeginnerOfChainFunction {

    default <T1, R> Function<T1, R> first(Supplier<R> function) {
        return k -> function.get();
    }

    default <T1, R> Function<T1, R> first(Function<T1, R> function) {
        return function;
    }

    default <T1, T2, R> BiFunction<T1, T2, R> first(BiFunction<T1, T2, R> function) {
        return function;
    }

    default <T1, T2, T3, R> Function3<T1, T2, T3, R> first(Function3<T1, T2, T3, R> function) {
        return function;
    }

    default <T1, T2, T3, T4, R> Function4<T1, T2, T3, T4, R> first(Function4<T1, T2, T3, T4, R> function) {
        return function;
    }

    default <T1, T2, T3, T4, T5, R> Function5<T1, T2, T3, T4, T5, R> first(Function5<T1, T2, T3, T4, T5, R> function) {
        return function;
    }

    default <T1, T2, T3, T4, T5, T6, R> Function6<T1, T2, T3, T4, T5, T6, R> first(Function6<T1, T2, T3, T4, T5, T6, R> function) {
        return function;
    }

    default <T1, T2, T3, T4, T5, T6, T7, R> Function7<T1, T2, T3, T4, T5, T6, T7, R> first(Function7<T1, T2, T3, T4, T5, T6, T7, R> function) {
        return function;
    }

    default <T1, T2, T3, T4, T5, T6, T7, T8, R> Function8<T1, T2, T3, T4, T5, T6, T7, T8, R> first(Function8<T1, T2, T3, T4, T5, T6, T7, T8, R> function) {
        return function;
    }

}
