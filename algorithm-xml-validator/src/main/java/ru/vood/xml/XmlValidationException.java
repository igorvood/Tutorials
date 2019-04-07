package ru.vood.xml;

public class XmlValidationException extends RuntimeException {

    public XmlValidationException(Throwable cause) {
        super(cause);
    }

    public XmlValidationException(String message) {
        super(message);
    }

    public XmlValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
