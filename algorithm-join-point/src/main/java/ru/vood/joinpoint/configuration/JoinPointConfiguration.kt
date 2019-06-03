package ru.vood.joinpoint.configuration

import org.springframework.boot.task.TaskSchedulerBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler

@Configuration
private open class JoinPointConfiguration {

    @Bean("jpThreadPoolTaskScheduler")
    open fun getThreadPoolTaskScheduler(): ThreadPoolTaskScheduler {
        return TaskSchedulerBuilder()
                .poolSize(100)
                .threadNamePrefix("jp-thread")
                .build()
    }

}