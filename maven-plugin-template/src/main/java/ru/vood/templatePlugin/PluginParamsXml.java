package ru.vood.templatePlugin;


import ru.vood.templatePlugin.generated.fromXSD.PluginTines;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

public class PluginParamsXml {
    private final JAXBContext jaxbContext;

    public PluginParamsXml() throws JAXBException {
        jaxbContext = JAXBContext.newInstance(PluginTines.class);
    }

    public String objToXml(PluginTines request) {
        return jaxbElementToXml(request);
    }

    public PluginTines xmlToObj(String xml) throws JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        StringReader sr = new StringReader(xml);
        return (PluginTines) unmarshaller.unmarshal(sr);
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
