package ru.vood.responce

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement

open class RouteResponseApplication

fun main(args: Array<String>) {
    SpringApplication.run(RouteResponseApplication::class.java, *args)
}