package com.example.openmapvalidator.service.request;

import com.example.openmapvalidator.helper.ConfigurationService;
import com.example.openmapvalidator.helper.Const;
import com.example.openmapvalidator.service.file.FileHandler;
import com.example.openmapvalidator.service.file.XMLFileParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * @author Sanan.Ahmadzada
 */
@Service
public class OpenStreetMapRequestHandler implements OpenstreetRequestHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenStreetMapRequestHandler.class);

    private final RestTemplate restTemplate;
    private final ConfigurationService configurationService;
    private final XMLFileParser xmlFileParserImpl;
    private final FileHandler fileHandlerImpl;

    @Autowired
    public OpenStreetMapRequestHandler(RestTemplate restTemplate, ConfigurationService configurationService,
                                       XMLFileParser xmlFileParserImpl, FileHandler fileHandlerImpl) {
        this.restTemplate = restTemplate;
        this.configurationService = configurationService;
        this.xmlFileParserImpl = xmlFileParserImpl;
        this.fileHandlerImpl = fileHandlerImpl;
    }

    public Map<String, String> handle(String osmId) throws IOException, SAXException,
            ParserConfigurationException {

        Map<String, String> longAndLatMap = new HashMap<>();

        String openStreetUriGet = configurationService.getOPENSTREET_URI_GET_LONG_WITH_OSM_ID()
                .replace(Const.OSMID_REPLACEMENT_SHORTCUT, osmId);

        String result = restTemplate.getForObject(openStreetUriGet, String.class);

        File tmpFile = fileHandlerImpl.createTmpFileAndPutContent(result);

        Document doc = xmlFileParserImpl.getDocumentForXmlParse(tmpFile);
        NodeList nodeList = doc.getElementsByTagName("node");
        //now XML is loaded as Document in memory, lets convert it to Object List
        NamedNodeMap map = nodeList.item(0).getAttributes();
        String LAT = map.getNamedItem("lat").getNodeValue();
        String LON = map.getNamedItem("lon").getNodeValue();

        longAndLatMap.put(Const.LATITUDE, LAT);
        longAndLatMap.put(Const.LONGITUDE, LON);

        return longAndLatMap;
    }

}
