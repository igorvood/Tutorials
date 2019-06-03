package ru.vood.joinpoint.configuration.wrap;

import io.vavr.Function3;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public interface FunctionalWrapper extends BeginnerOfChainFunction {

    default <R> R wrap(Supplier<R> f1) {
        try {
            return getOk(f1.get());
        } catch (Exception e) {
            return getError(e);
        }
    }

    default <T1, R> R wrap(Function<T1, R> f1, T1 t1) {
        try {
            return getOk(f1.apply(t1));
        } catch (Exception e) {
            return getError(e);
        }
    }

    default <T1, T2, R> R wrap(BiFunction<T1, T2, R> f2, T1 t1, T2 t2) {
        try {
            return getOk(f2.apply(t1, t2));
        } catch (Exception e) {
            return getError(e);
        }
    }

    default <T1, T2, T3, R> R wrap(Function3<T1, T2, T3, R> f3, T1 t1, T2 t2, T3 t3) {
        try {
            return getOk(f3.apply(t1, t2, t3));
        } catch (Exception e) {
            return getError(e);
        }
    }

    default <R> R getError(Exception e) {
        throw new RuntimeException(e.getMessage(), e);
    }

    default <R> R getOk(R apply) {
        return apply;
    }

}
