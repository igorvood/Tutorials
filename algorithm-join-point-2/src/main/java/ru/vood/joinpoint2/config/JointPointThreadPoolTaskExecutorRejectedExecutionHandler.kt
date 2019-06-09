package ru.vood.joinpoint2.config

import java.util.concurrent.RejectedExecutionHandler
import java.util.concurrent.ThreadPoolExecutor


class JointPointThreadPoolTaskExecutorRejectedExecutionHandler : RejectedExecutionHandler {
    override fun rejectedExecution(r: Runnable?, executor: ThreadPoolExecutor?) {

//        if (executor === snkExecutor.getThreadPoolExecutor()) {

//            LOGGER.warn("Sink thread pool exceeded max size {}", executor.maximumPoolSize)

        try {

            // мы уже в пуле jms

            r!!.run()

        } catch (e: Exception) {

            //              LOGGER.error("Attempt to execute the sink pool's thread in the jms pool has failed: {}", e)

        }

//        }


    }
}