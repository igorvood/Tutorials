package ru.vood.joinpoint.configuration.infrastructure.jp

import org.springframework.stereotype.Component

@Component("Bean1")
class JoinPoint1ServiceImpl : JoinPointService<Int, String> {
    override fun runJoinPoint(inCtx: Int): String {
        return Int.toString()
    }
}