package com.example.openmapvalidator.service.statistic;

import com.example.openmapvalidator.model.open.GeographicRectangle;
import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/**
 *  * @author Sanan.Ahmadzada
 */
@Service
public class RadiusHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RadiusHandler.class);

    public Map<String, String> handle(GeographicRectangle rectangle) {

        if (rectangle == null) {
            return null;
        }

        Map<String, String>  geographicValueMap = new HashMap<>();

        //<bounds minlat="48.1789100" minlon="16.3248500" maxlat="48.1801300" maxlon="16.3277300"/>

        //maxlat, minlon      maxlat, maxlon
        //minlat, minlon      minlat, maxlon

        double heightLatitudeUp = Double.valueOf(rectangle.getMaxLatitude());
        double heightLongitudeUp = Double.valueOf(rectangle.getMinLongitude());

        double heightLatitudeDown = Double.valueOf(rectangle.getMinLatitude());
        double heightLongitudeDown = Double.valueOf(rectangle.getMinLongitude());

        double widthLatitudeLeft = Double.valueOf(rectangle.getMinLatitude());
        double widthLongitudeLeft = Double.valueOf(rectangle.getMinLongitude());
        double widthLatitudeRight = Double.valueOf(rectangle.getMinLatitude());
        double widthLongitudeRight = Double.valueOf(rectangle.getMaxLongitude());

        LatLng heightPointUp = new LatLng(heightLatitudeUp, heightLongitudeUp);
        LatLng heightPointDown = new LatLng(heightLatitudeDown, heightLongitudeDown);
        double height = LatLngTool.distance(heightPointUp, heightPointDown, LengthUnit.METER);

        LatLng widthPointLeft = new LatLng(widthLatitudeLeft, widthLongitudeLeft);
        LatLng widthPointRight = new LatLng(widthLatitudeRight, widthLongitudeRight);
        double width = LatLngTool.distance(widthPointLeft, widthPointRight, LengthUnit.METER);

        LOGGER.info("width : {}", width);
        LOGGER.info("height : {}", height);

        Double radius = 0.5 * Math.sqrt(width * width + height * height);

        LOGGER.info("radius : {}", radius);
        geographicValueMap.put("radius", radius.toString());

        double bearingWidth = LatLngTool.initialBearing(widthPointLeft, widthPointRight);
        double bearingHeight = LatLngTool.initialBearing(heightPointUp, heightPointDown);

        LatLng widthMidPoint = LatLngTool.travel(widthPointLeft, bearingWidth, width / 2, LengthUnit.METER);
        LatLng heightMidPoint = LatLngTool.travel(heightPointUp, bearingHeight, height / 2, LengthUnit.METER);

        geographicValueMap.put("latitude", "" + heightMidPoint.getLatitude());
        geographicValueMap.put("longitude", "" + widthMidPoint.getLongitude());

        LOGGER.info("{}, {}", heightMidPoint.getLatitude(), widthMidPoint.getLongitude());
        return geographicValueMap;
    }
}
