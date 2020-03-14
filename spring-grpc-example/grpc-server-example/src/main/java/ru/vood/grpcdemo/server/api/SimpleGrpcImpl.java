package ru.vood.grpcdemo.server.api;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.vood.grpc.examples.lib.HelloReply;
import ru.vood.grpc.examples.lib.HelloRequest;
import ru.vood.grpc.examples.lib.SimpleGrpc;

@GrpcService
public class SimpleGrpcImpl extends SimpleGrpc.SimpleImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        HelloReply build = HelloReply.newBuilder()
                .setMessage("Hellow " + request.getName())
                .build();
        responseObserver.onNext(build);
        responseObserver.onCompleted();

    }
}
