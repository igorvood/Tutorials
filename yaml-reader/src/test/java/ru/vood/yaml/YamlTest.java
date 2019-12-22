package ru.vood.yaml;

import org.junit.Test;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

public class YamlTest {

    @Test
    public void
    whenLoadYAMLDocumentWithTopLevelClass_thenLoadCorrectJavaObjectWithNestedObjects() {

        Yaml yaml = new Yaml(new Constructor(Customer.class));
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("yaml/customer_with_contact_details_and_address.yaml");
        Customer customer = yaml.load(inputStream);

        assertNotNull(customer);
        assertEquals("John", customer.getFirstName());
        assertEquals("Doe", customer.getLastName());
        assertEquals(31, customer.getAge());
        assertNotNull(customer.getContactDetails());
        assertEquals(2, customer.getContactDetails().size());

        assertEquals("mobile", customer.getContactDetails()
                .get(0)
                .getType());
        assertEquals(123456789, customer.getContactDetails()
                .get(0)
                .getNumber());
        assertEquals("landline", customer.getContactDetails()
                .get(1)
                .getType());
        assertEquals(456786868, customer.getContactDetails()
                .get(1)
                .getNumber());
        assertNotNull(customer.getHomeAddress());
        assertEquals("Xyz, DEF Street", customer.getHomeAddress()
                .getLine());


        final String dump = yaml.dump(customer);
        System.out.println(dump);
    }

}
