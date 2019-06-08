package ru.vood.joinpoint2

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement
open class JoinPointApplication

fun main(args: Array<String>) {
    SpringApplication.run(JoinPointApplication::class.java, *args)
}