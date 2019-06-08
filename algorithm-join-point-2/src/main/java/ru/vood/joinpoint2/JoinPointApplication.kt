package ru.vood.joinpoint2

@SpringBootApplication
@EnableTransactionManagement
class JoinPointApplication {
    @JvmStatic
    fun main(args: Array<String>) {
        SpringApplication.run(JoinPointApplication::class.java, *args)
    }
}