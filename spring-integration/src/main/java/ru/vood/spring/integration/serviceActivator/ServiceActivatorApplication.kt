package ru.vood.spring.integration.serviceActivator

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ImportResource
import org.springframework.integration.annotation.IntegrationComponentScan
import org.springframework.integration.config.EnableIntegration
import org.springframework.integration.config.EnablePublisher
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
@EnableIntegration
@EnablePublisher
@IntegrationComponentScan
@ImportResource("classpath*:integration/integration-service-activator.xml")
open class ServiceActivatorApplication

fun main(args: Array<String>) {
    SpringApplication.run(ServiceActivatorApplication::class.java, *args)
}