package ru.vood.spring.integration.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.channel.ExecutorChannel
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.dsl.IntegrationFlows
import ru.vood.spring.integration.splitter.SplitterOne
import ru.vood.spring.integration.transformer.TransformerOne
import ru.vood.spring.integration.transformer.TransformerTwo
import java.util.concurrent.Executor
import java.util.concurrent.ForkJoinPool


@Configuration
open class ConfigurationFlowByDsl(@Autowired
                                  var transformerOne: TransformerOne,
                                  @Autowired
                                  var transformerTwo: TransformerTwo,
                                  @Autowired
                                  var splitterOne: SplitterOne) {


    @Bean
    open fun requestChannel(): DirectChannel {
        return DirectChannel()
    }

    @Bean
    open fun executor(): Executor {
        return ForkJoinPool(10)
    }

    @Bean
    open fun requestChannelExecutor(): ExecutorChannel {
        return ExecutorChannel(executor())
    }

    fun upcase(): IntegrationFlow {
        return IntegrationFlow { f ->
            f.channel(requestChannel())
                    .transform(transformerOne, "transform")
                    .split(splitterOne)
                    .channel(requestChannelExecutor())
                    .transform(transformerTwo)
                    .log()
        }
    }


    @Bean
    open fun dslFlow(): IntegrationFlow {
        return IntegrationFlows.from(requestChannel())
                .transform(transformerOne, "transform")
                .split(splitterOne)
                .channel(requestChannelExecutor())
                .transform(transformerTwo)
                .log()
                //.channel("")
                .get()
/*
        val transform = transform1
                .split(splitterOne::split)
                .get()
*/
        //.transform(transformerTwo::transform)


/*
        return IntegrationFlow { f ->
            f.split()                                         // 1
                    .transform(GenericTransformer<String, String> { it.toUpperCase() })  // 2
                    .aggregate()
        }                                    // 3
*/
    }
}