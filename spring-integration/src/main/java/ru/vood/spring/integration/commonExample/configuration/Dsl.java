package ru.vood.spring.integration.commonExample.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlowDefinition;
import ru.vood.spring.integration.commonExample.transformer.TransformerOne;

@Configuration
public class Dsl {

    @Bean
    public IntegrationFlow upcase() {
        return f -> {
            IntegrationFlowDefinition<?> transform = f.transform(new TransformerOne());
            IntegrationFlowDefinition<?> split = transform.split();
            split                                         // 1
                    .<String, String>transform(String::toUpperCase)  // 2
                    .aggregate();
        };                                    // 3
    }
}
