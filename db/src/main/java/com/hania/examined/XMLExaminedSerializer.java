package com.hania.examined;

import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class XMLExaminedSerializer {

    private XStream xStream;

    public Examined read(File file) throws SerializationException {
        xStream = new ExaminedXStream();
        try {
            return (Examined) xStream.fromXML(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new SerializationException(e.getMessage());
        }
    }

    public void save(File file, Examined order) throws SerializationException {
        xStream = new ExaminedXStream();
        try {
            xStream.toXML(order, new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            throw new SerializationException(e.getMessage());
        }
    }
}
