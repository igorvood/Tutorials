package org.baeldung;

import com.baeldung.si.security.MessageConsumer;
import com.baeldung.si.security.SecuredDirectChannel;
import com.baeldung.si.security.SecurityConfig;
import com.baeldung.si.security.SecurityPubSubChannel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SecurityConfig.class, SecuredDirectChannel.class, SecurityPubSubChannel.class,
        MessageConsumer.class})
public class SpringContextIntegrationTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
