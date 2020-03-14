package ru.vood.grpcdemo.client

import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.vood.grpc.examples.lib.SimpleGrpc.SimpleBlockingStub

@Configuration
class GrpcClientConfiguration {

    @Bean
    @GrpcClient("local-grpc-server")
    fun simpleBlockingStub(simpleBlockingStub: SimpleBlockingStub) = simpleBlockingStub

}