package ru.vood.joinpoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class JoinPointApplication {
    public static void main(String[] args) {
        SpringApplication.run(JoinPointApplication.class, args);
    }
}
