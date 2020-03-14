package ru.vood.grpcdemo.client;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

@Service
public class GetNameService {

    private String name;

    @PostConstruct
    public void init() {
        System.out.println("file " + new Date());
        String s3 = "";
        try {

            s3 = new String(Files.readAllBytes(Paths.get("C:\\temp\\grpc-demo\\grpc-client-example\\src\\main\\resources\\6_0_mb.txt")));
//            s3 = Files.readAllLines(new File("C:\\temp\\21\\camunda.zip").toPath())
//                    .stream()
//                    .reduce((s1, s2) -> s1 + s2)
//                    .get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(new Date() + " length " + s3.length());
//        final String s = IntStream.range(1, 1024 * 1024 )
//                .parallel()
//                .mapToObj(q -> "12345678901234567890123456789012345678901234567890")
//                .reduce((s1, s2) -> s1 + s2)
//                .get();
//        System.out.println(new Date());
        name = s3;

    }

    public String getName() {
        return name;
    }
}
