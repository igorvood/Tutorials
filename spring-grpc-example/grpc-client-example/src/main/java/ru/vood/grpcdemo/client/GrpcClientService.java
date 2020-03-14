
package ru.vood.grpcdemo.client;

import io.grpc.StatusRuntimeException;
import org.springframework.stereotype.Service;
import ru.vood.grpc.examples.lib.HelloReply;
import ru.vood.grpc.examples.lib.HelloRequest;
import ru.vood.grpc.examples.lib.SimpleGrpc;

import java.util.Date;

/**
 * @author Michael (yidongnan@gmail.com)
 * @since 2016/11/8
 */
@Service
public class GrpcClientService {

    private final SimpleGrpc.SimpleBlockingStub simpleStub;

    private final GetNameService getNameService;

    public GrpcClientService(SimpleGrpc.SimpleBlockingStub simpleStub, GetNameService getNameService) {
        this.simpleStub = simpleStub;
        this.getNameService = getNameService;
    }

    public String sendMessage(final String name) {
        try {
            System.out.println("GrpcClientService.sendMessage begin " + new Date());
            final HelloReply response = this.simpleStub.sayHello(HelloRequest.newBuilder().setName(getNameService.getName()).build());
            System.out.println("GrpcClientService.sendMessage end " + new Date());
            return String.valueOf(response.getMessage().length());
        } catch (final StatusRuntimeException e) {
            return "FAILED with " + e.getStatus().getCode().name();
        }
    }

}
