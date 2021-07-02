package ru.vood.spring.cache.service

interface DatabaseService {

    fun getData(i: Long): Long
}