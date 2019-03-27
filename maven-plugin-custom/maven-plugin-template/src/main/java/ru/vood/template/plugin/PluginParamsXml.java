package ru.vood.template.plugin;

import ru.vood.template.plugin.generated.from.xsd.PluginTines;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

public class PluginParamsXml {
    private final JAXBContext jaxbContext;

    public PluginParamsXml() {
        try {
            jaxbContext = JAXBContext.newInstance(PluginTines.class);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public String objToXml(PluginTines request) {
        return jaxbElementToXml(request);
    }

    public PluginTines xmlToObj(String xml) {
        try {
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StringReader sr = new StringReader(xml);
            return (PluginTines) unmarshaller.unmarshal(sr);

        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    private String jaxbElementToXml(Object jaxbElement) {
        try {
            StringWriter sw = new StringWriter();
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(jaxbElement, sw);
            return sw.toString();
        } catch (JAXBException e) {
            throw new XmlLoadException(e);
        }
    }
}
