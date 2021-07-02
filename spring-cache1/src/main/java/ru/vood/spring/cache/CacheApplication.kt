package ru.vood.spring.cache

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cache.annotation.EnableCaching
import ru.vood.spring.cache.service.DatabaseService

@SpringBootApplication
@EnableCaching
open class CacheApplication: CommandLineRunner {
    @Autowired
    lateinit var databaseService: DatabaseService

    override fun run(vararg args: String?) {
        println(databaseService.getData(1))
        println(databaseService.getData(1))
    }

}
fun main(args: Array<String>) {
    SpringApplication.run(CacheApplication::class.java, *args)

}

