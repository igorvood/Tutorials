package ru.vood.responce

import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ImportResource
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.messaging.MessageChannel
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.ContextHierarchy
import org.springframework.test.context.junit4.SpringRunner
import ru.vood.responce.common.RandomizedDelayTrigger
import ru.vood.responce.common.ServiceResolver
import ru.vood.responce.common.ServiceResolverService
import ru.vood.responce.config.ConfigurationDatabaseTest
import ru.vood.responce.configuration.RouteConfiguration
import ru.vood.responce.handler.HandleException
import ru.vood.responce.handler.HandleExceptionService
import ru.vood.responce.response.ResponseBuilderService
import ru.vood.responce.response.ResponseBuilderServiceImpl
import ru.vood.responce.send.SenderService
import ru.vood.responce.send.SenderServiceImpl

@SpringBootTest
@RunWith(SpringRunner::class)

@ContextHierarchy(
        ContextConfiguration(classes = [RouteConfiguration::class]),
        ContextConfiguration(classes = [ConfigurationDatabaseTest::class])
)
@ImportResource("classpath*:integration/integration.xml")
class RouteResponseApplicationTestConfiguration {

    @Bean
    fun getResponseBuilderService(): ResponseBuilderService {
        return ResponseBuilderServiceImpl()
    }

    @Bean
    fun getServiceResolver(jdbcTemplate: JdbcTemplate): ServiceResolverService {
        return ServiceResolver(jdbcTemplate)
    }

    @Bean
    fun getHandleService(jdbcTemplate: JdbcTemplate): HandleExceptionService {
        return HandleException(getServiceResolver(jdbcTemplate), getResponseBuilderService())
    }

    @Bean("senderService")
    fun getSenderService(
            @Qualifier("commonResponseChannel")
            messageChannel: MessageChannel,
            jdbcTemplate: JdbcTemplate
    ): SenderService {
        return SenderServiceImpl(messageChannel, getServiceResolver(jdbcTemplate), getHandleService(jdbcTemplate))
    }

    @Bean
    fun getRandomizedDelayTrigger() = RandomizedDelayTrigger()
}