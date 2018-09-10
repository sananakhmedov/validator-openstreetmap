package com.example.openmapvalidator.service.request;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Map;
/**
 * @author Sanan.Ahmadzada
 */
public interface OpenstreetRequestHandler {
    /**
     * Get latitude and longitude of a place which is stored in db retrieve with osm_id
     *
     * @param osmId openstreet map id
     * @return
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    Map<String, String> handle(String osmId) throws IOException, SAXException,
            ParserConfigurationException;
}
