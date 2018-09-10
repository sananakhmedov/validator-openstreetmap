package com.example.openmapvalidator.service.statistic;

import com.example.openmapvalidator.helper.Const;
import com.example.openmapvalidator.model.open.GeographicRectangle;
import com.example.openmapvalidator.service.file.XMLFileParser;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class StatisticHandlerImpl implements StatisticHandler {

    private final GoogleNearbyRequestHandler googleNearbyRequestHandler;
    private final RadiusHandler radiusHandler;
    private final OpenmapRequestHandler openStreetMapRequestHandler;
    private final XMLFileParser xmlFileParserImpl;

    public StatisticHandlerImpl(GoogleNearbyRequestHandler googleNearbyRequestHandler, RadiusHandler radiusHandler,
                                OpenmapRequestHandler openStreetMapRequestHandler, XMLFileParser xmlFileParserImpl) {
        this.googleNearbyRequestHandler = googleNearbyRequestHandler;
        this.radiusHandler = radiusHandler;
        this.openStreetMapRequestHandler = openStreetMapRequestHandler;
        this.xmlFileParserImpl = xmlFileParserImpl;
    }

    public Map<String, Integer> handle(String fileName) {
        fileName = fileName.trim().replaceAll("\\s","");

        GeographicRectangle rectangle = null;
        try {
            rectangle = xmlFileParserImpl.parseRectangleCoordinates(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        Map<String, String> geographicValueMap = radiusHandler.handle(rectangle);

        String lat = geographicValueMap.get(Const.LATITUDE);
        String lon = geographicValueMap.get(Const.LONGITUDE);
        String radius = geographicValueMap.get(Const.RADIUS);

        int numOfGooglePlaces = 0;
        int numOfOpenstreetMapPlaces = 0;

        try {
            numOfGooglePlaces = googleNearbyRequestHandler.handleNearbyWithRadius(lat, lon, radius);
            numOfOpenstreetMapPlaces = openStreetMapRequestHandler.countOpenmapPlaces();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Map<String, Integer> statisticValueMap = new HashMap<>();
        statisticValueMap.put(Const.NUMBER_OF_GOOGLE_PLACES, numOfGooglePlaces);
        statisticValueMap.put(Const.NUMBER_OF_OPENMAP_PLACES, numOfOpenstreetMapPlaces);

        return statisticValueMap;
    }
}
