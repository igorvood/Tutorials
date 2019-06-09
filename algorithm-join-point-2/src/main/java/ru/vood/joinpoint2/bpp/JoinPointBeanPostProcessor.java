package ru.vood.joinpoint2.bpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.vood.joinpoint2.infrastructure.bean.WorkerBeanInterface;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

//@Component
public class JoinPointBeanPostProcessor implements BeanPostProcessor {

    private final JdbcTemplate jdbcTemplate;
    private final Map<String, WorkerBeanInterface> beanMap;

    public JoinPointBeanPostProcessor(JdbcTemplate jdbcTemplate, ApplicationContext context, Map<String, WorkerBeanInterface> beanMap) {
        this.jdbcTemplate = jdbcTemplate;
        this.beanMap = beanMap;
    }


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if (beanName.equals("Bean1")) {
            System.out.println(beanName);
            final List<Type> collect = Arrays.stream(bean.getClass().getInterfaces())
                    .filter(new Predicate<Type>() {
                        @Override
                        public boolean test(Type type) {
                            return type == WorkerBeanInterface.class;
                        }
                    })
                    .collect(toList());
            System.out.println(collect);
        }
//        if (beanMap.containsKey(beanName)) {
//            final List<Type> collect = Arrays.stream(bean.getClass().getGenericInterfaces())
//                    .collect(toList());
//            System.out.println(collect);
//        }
        return bean;
    }
}
