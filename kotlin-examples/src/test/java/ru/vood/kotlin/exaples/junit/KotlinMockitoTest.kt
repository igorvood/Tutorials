package ru.vood.kotlin.exaples.junit

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.junit.Test
import org.mockito.Mockito.`when` as on


class KotlinMockitoTest {

    @Test
    fun doSomething() {
        val mock = mock<MyClass> { on { getText() } doReturn "Text" }

        val classUnderTest = ClassUnderTest(mock)
        classUnderTest.doAction()

    }

}