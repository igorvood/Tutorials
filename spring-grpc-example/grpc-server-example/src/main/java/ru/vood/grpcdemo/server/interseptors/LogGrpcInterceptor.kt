package ru.vood.grpcdemo.server.interseptors

import io.grpc.Metadata
import io.grpc.ServerCall
import io.grpc.ServerCallHandler
import io.grpc.ServerInterceptor
import org.slf4j.LoggerFactory

class LogGrpcInterceptor : ServerInterceptor {
    override fun <ReqT, RespT> interceptCall(serverCall: ServerCall<ReqT, RespT>, metadata: Metadata,
                                             serverCallHandler: ServerCallHandler<ReqT, RespT>): ServerCall.Listener<ReqT> {
        log.info("===========================>" + serverCall.methodDescriptor.fullMethodName)
        return serverCallHandler.startCall(serverCall, metadata)
    }

    companion object {
        private val log = LoggerFactory.getLogger(LogGrpcInterceptor::class.java)
    }
}