package ru.vood.joinpoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableTransactionManagement
public class JoinPointApplication {
    public static void main(String[] args) {

        List<String> strings = new ArrayList<>();
        final Object[] objects = strings.toArray();

        SpringApplication.run(JoinPointApplication.class, args);


    }
}
