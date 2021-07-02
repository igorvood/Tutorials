package ru.vood.spring.cache.service

import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
open class DatabaseServiceImpl : DatabaseService {


    @Cacheable("qwe")
    override fun getData(i: Long): Long {
        println("Вычисление ")
        return i
    }

}