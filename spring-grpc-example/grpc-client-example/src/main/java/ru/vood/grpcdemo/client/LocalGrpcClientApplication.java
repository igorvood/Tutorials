package ru.vood.grpcdemo.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Michael (yidongnan@gmail.com)
 * @since 2016/11/8
 */
@EnableScheduling
@SpringBootApplication
public class LocalGrpcClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocalGrpcClientApplication.class, args);
    }

}
