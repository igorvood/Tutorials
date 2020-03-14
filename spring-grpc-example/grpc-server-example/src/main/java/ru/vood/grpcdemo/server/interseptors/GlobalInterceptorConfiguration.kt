package ru.vood.grpcdemo.server.interseptors

import net.devh.boot.grpc.server.interceptor.GlobalServerInterceptorConfigurer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GlobalInterceptorConfiguration {
    @Bean
    fun globalInterceptorConfigurerAdapter(): GlobalServerInterceptorConfigurer {
        return GlobalServerInterceptorConfigurer { registry -> registry.addServerInterceptors(LogGrpcInterceptor()) }
    }
}