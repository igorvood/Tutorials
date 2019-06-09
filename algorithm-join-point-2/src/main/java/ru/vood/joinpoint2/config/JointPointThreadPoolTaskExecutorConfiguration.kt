package ru.vood.joinpoint2.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

@Configuration
open class JointPointThreadPoolTaskExecutorConfiguration {

    @Bean("jpThreadPool")
    open fun getJointPointThreadPoolTaskExecutor(): ThreadPoolTaskExecutor {
        val exec = ThreadPoolTaskExecutor()
        exec.corePoolSize = 1
        exec.setQueueCapacity(100)
        exec.maxPoolSize = 10
        exec.keepAliveSeconds = 10
        exec.threadNamePrefix = "JP"
        exec.threadGroup = ThreadGroup("JointPointThread")
        exec.setRejectedExecutionHandler(JointPointThreadPoolTaskExecutorRejectedExecutionHandler())
        return exec
    }
}