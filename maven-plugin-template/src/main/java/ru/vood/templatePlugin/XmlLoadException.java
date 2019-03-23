package ru.vood.templatePlugin;

public class XmlLoadException extends RuntimeException {
    public XmlLoadException(String message) {
        super(message);
    }

    public XmlLoadException(Throwable cause) {
        super(cause);
    }
}
