package ru.vood.joinpoint2.config

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class MapperConfiguration {

    @Bean("gsonMapper")
    open fun get(): Gson {
        return GsonBuilder()
                .setPrettyPrinting()
                .create()
    }

}