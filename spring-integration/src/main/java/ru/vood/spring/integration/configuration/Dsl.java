package ru.vood.spring.integration.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlowDefinition;
import ru.vood.spring.integration.transformer.TransformerOne;

@Configuration
public class Dsl {

    @Autowired
    TransformerOne transformerOne;

    @Bean
    public IntegrationFlow upcase() {
        return f -> {
            IntegrationFlowDefinition<?> transform = f.transform(transformerOne);
            IntegrationFlowDefinition<?> split = transform.split();
            split                                         // 1
                    .<String, String>transform(String::toUpperCase)  // 2
                    .aggregate();
        };                                    // 3
    }
}
