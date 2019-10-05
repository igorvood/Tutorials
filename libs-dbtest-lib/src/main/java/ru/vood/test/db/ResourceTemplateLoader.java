package ru.vood.test.db;

import freemarker.cache.ClassTemplateLoader;

import java.io.IOException;

public class ResourceTemplateLoader extends ClassTemplateLoader {
    public ResourceTemplateLoader() {
        super(ResourceTemplateLoader.class, "/");
    }

    @Override
    public Object findTemplateSource(String name) throws IOException {
        if (!name.startsWith("@")) {
            throw new IOException(String.format("Template name \"%s\" must start with @", name));
        } else {
            return super.findTemplateSource(name.substring(1));
        }
    }
}
