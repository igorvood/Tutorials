package ru.vood.grpcdemo.client;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScedullerService {

    private final GrpcClientService grpcClientService;

    public ScedullerService(GrpcClientService grpcClientService) {
        this.grpcClientService = grpcClientService;
    }

    @Scheduled(fixedRate = 10000)
    public void sas() {
        String vvvvvvvv = grpcClientService.sendMessage("vvvvvvvv");
        System.out.println(vvvvvvvv);
    }

//    @Scheduled(fixedRate = 1)
//    public void sas2() {
//        String vvvvvvvv = grpcClientService.sendMessage("vvvvvvvv");
//        System.out.println("========"+vvvvvvvv);
//    }
//
//    @Scheduled(fixedRate = 1)
//    public void sas3() {
//        String vvvvvvvv = grpcClientService.sendMessage("vvvvvvvv");
//        System.out.println("========"+vvvvvvvv);
//    }
//    @Scheduled(fixedRate = 1)
//    public void sas4() {
//        String vvvvvvvv = grpcClientService.sendMessage("vvvvvvvv");
//        System.out.println("========"+vvvvvvvv);
//    }
//
//    @Scheduled(fixedRate = 1)
//    public void sas5() {
//        String vvvvvvvv = grpcClientService.sendMessage("vvvvvvvv");
//        System.out.println("========"+vvvvvvvv);
//    }

}
