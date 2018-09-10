package com.example.openmapvalidator.service.file;

import com.example.openmapvalidator.model.open.GeographicRectangle;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public interface XMLFileParser {
    /**
     * returns rectangle min and max latitude and longitudes coordinates by parsing given osm map file
     * @param fileName
     * @return
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    GeographicRectangle parseRectangleCoordinates(String fileName)
            throws IOException, SAXException, ParserConfigurationException;

    Document getDocumentForXmlParse(File file) throws ParserConfigurationException, IOException, SAXException;
}
