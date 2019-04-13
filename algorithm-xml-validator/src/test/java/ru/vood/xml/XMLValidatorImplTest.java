package ru.vood.xml;


import org.junit.Before;
import org.junit.Test;

public class XMLValidatorImplTest {

    private XMLValidatorImpl xmlValidator;

    @Before
    public void before() {
        xmlValidator = new XMLValidatorImpl();
    }

    @Test
    public void validate() {
        xmlValidator.validate("test.xml", "test.xsd");
    }

    @Test(expected = XmlValidationException.class)
    public void validateError() {
        xmlValidator.validate("test.error.xml", "test.xsd");
    }

}
