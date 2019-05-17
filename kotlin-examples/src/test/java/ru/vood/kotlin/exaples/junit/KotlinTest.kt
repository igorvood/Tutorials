package ru.vood.kotlin.exaples.junit


import org.junit.ClassRule
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class KotlinTest {
    //    Компанон служит для объявлений статических полей и методов в классе
    companion object {
        @JvmStatic
        @BeforeAll
        fun setup() {
            println("Before All init() method called")
        }

        @ClassRule
        @JvmField
        val SPRING_CLASS_RULE = "123"
    }

    @ClassRule
    @JvmField
    var springMethodRule = "123"


    @Test
    fun test() {
        println("Test")
    }
}