package ru.vood.joinpoint.configuration.infrastructure.jp

import org.springframework.stereotype.Component

@Component("Bean1")
class BeanInterfaceImpl : BeanInterface<Int, String> {
    override fun run(inCtx: Int): String {
        return Int.toString()
    }
}