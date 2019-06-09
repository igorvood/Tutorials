package ru.vood.joinpoint2.bpp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.vood.joinpoint2.infrastructure.bean.WorkerBeanInterface;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class CheckMeta {
    private final HashMap<String, WorkerBeanInterface> beanMap;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CheckMeta(Map<String, WorkerBeanInterface> beanMap, JdbcTemplate jdbcTemplate) {
        this.beanMap = new HashMap<>(beanMap);
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    private void check() {
        System.out.println(beanMap);

    }
}
