package ru.vood.joinpoint2.infrastructure.bean

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.test.context.ContextConfiguration
import ru.vood.joinpoint2.config.JointPointThreadPoolTaskExecutorRejectedExecutionHandler

@ContextConfiguration
@ComponentScan("ru.vood.joinpoint2.infrastructure")
class BeanRunnerTestConfiguration {

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