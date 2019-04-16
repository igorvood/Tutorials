package ru.vood.joinpoint.configuration.beanfactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
public class sadasd {

    @Autowired
    Map<String, JoinPointState> joinPointStates;

    @PostConstruct
    public void qww() {
        System.out.println(joinPointStates);
    }

}
