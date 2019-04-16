package ru.vood.joinpoint.configuration.beanfactory;

import javafx.util.Pair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JointPointConfigurationBeanFactoryPostProcessorJava implements BeanFactoryPostProcessor {

    private static final Log logger = LogFactory.getLog(JointPointConfigurationBeanFactoryPostProcessorJava.class);

    private ConfigurableListableBeanFactory beanFactory;

    private BeanDefinitionRegistry registry;

/*
    @Autowired
    private JdbcTemplate jdbcTemplate;
*/

    public JointPointConfigurationBeanFactoryPostProcessorJava() {
    }
/*
    public JointPointConfigurationBeanFactoryPostProcessorJava(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
*/

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        if (beanFactory instanceof BeanDefinitionRegistry) {
            this.beanFactory = beanFactory;
            this.registry = (BeanDefinitionRegistry) beanFactory;
            registerJoinPoint();
        } else if (logger.isWarnEnabled()) {
            logger.warn("BeanFactory is not a BeanDefinitionRegistry. " + "The default Spring Integration infrastructure beans are not going to be registered");
        }

    }

    private void registerJoinPoint() {

        List<Pair<String, String>> query = new ArrayList<>();
        query.add(new Pair<>("work", "работает"));
        query.add(new Pair<>("stop", "не работает"));

        for (Pair<String, String> q : query) {


            if (!this.beanFactory.containsBean(q.getKey())) {
                if (logger.isInfoEnabled()) {
                    logger.info("No bean named '" + q.getKey() +
                            "' has been explicitly defined. " +
                            "Therefore, a default ThreadPoolTaskScheduler will be created.");
                }
                BeanDefinition scheduler = BeanDefinitionBuilder.genericBeanDefinition(JoinPointState.class)
                        .addPropertyValue("id", q.getKey())
                        .addPropertyValue("desc", q.getValue())
                        .getBeanDefinition();

                this.registry.registerBeanDefinition(q.getKey(), scheduler);
            }
        }

    }
}
