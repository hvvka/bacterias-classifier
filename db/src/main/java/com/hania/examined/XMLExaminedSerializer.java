package com.hania.examined;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XMLExaminedSerializer {

    private static final Logger LOG = LoggerFactory.getLogger(XMLExaminedSerializer.class);

    private JAXBContext jaxbContext;

    public Examined read(File file) {
        try {
            jaxbContext = JAXBContext.newInstance(Examined.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (Examined) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            LOG.error("", e);
        }
        return null;
    }

    public void save(File file, ExaminedList examinedList) {
        try {
            jaxbContext = JAXBContext.newInstance(ExaminedList.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(examinedList, file);
        } catch (JAXBException e) {
            LOG.error("", e);
        }
    }
}
