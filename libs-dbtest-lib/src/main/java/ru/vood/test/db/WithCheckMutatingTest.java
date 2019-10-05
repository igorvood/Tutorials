package ru.vood.test.db;

public @interface WithCheckMutatingTest {
    boolean value() default true;
}
