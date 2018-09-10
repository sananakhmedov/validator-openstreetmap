package com.example.openmapvalidator.controller;

import com.example.openmapvalidator.service.MapPlacesValidationHandler;
import com.example.openmapvalidator.service.file.FileHandler;
import com.example.openmapvalidator.service.statistic.StatisticHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author Sanan.Ahmadzada
 */
@RestController
@RequestMapping("/maps")
public class MapController {

    private static final Logger logger = LoggerFactory.getLogger(MapController.class);

    private final FileHandler fileHandlerImpl;
    private final MapPlacesValidationHandler mapPlacesValidationHandlerService;
    private final StatisticHandler statisticHandler;
    private final Gson gson;

    public MapController(FileHandler fileHandlerImpl, MapPlacesValidationHandler mapPlacesValidationHandlerService,
                         StatisticHandler statisticHandler, Gson gson) {
        this.fileHandlerImpl = fileHandlerImpl;
        this.mapPlacesValidationHandlerService = mapPlacesValidationHandlerService;
        this.statisticHandler = statisticHandler;
        this.gson = gson;
    }

    @GetMapping
    public Map<String, Integer> statisticValues(@RequestParam String fileName) {

        return statisticHandler.handle(fileName);
        //return mockStatisticData();
    }

    @PostMapping
    public Map<String, Map<String, String>> uploadFile(@RequestParam MultipartFile file) {
        logger.info("NEW REQUEST");

        String fileName = fileHandlerImpl.saveFile(file);

        Map<String, Map<String, String>> map =
                mapPlacesValidationHandlerService.saveAndCallForPlaceCoordinates(fileName);

        logger.debug("****Returned Map Json");
        logger.debug(gson.toJson(map));

        return map;

        //return mockData();
    }

    private Map<String, Integer> mockStatisticData() {
        String json = "{\"numOfGooglePlaces\":8,\"numOfOpenstreetMapPlaces\":7}";
        // convert JSON string to Map
        Type type = new TypeToken<Map<String, Integer>>(){}.getType();
        return gson.fromJson(json, type);
    }

    private Map<String, Map<String, String>> mockData() {
        String json = "{\"48.1800796,16.3276520\":{\"foursquare\":\"NULL\",\"google\":\"Tiefgarage " +
                "F\u00FCchselhofpark\",\"openstreet\":\"F\u00FCchselhofparkgarage\"},\"48.1792934," +
                "16.3266492\":{\"foursquare\":\"NULL\",\"google\":\"UTOPIA\",\"openstreet\":\"Club UTOPIA\"},\"48.1798179,16.3273043\":{\"foursquare\":\"Hofer\",\"google\":\"HOFER\",\"openstreet\":\"Hofer\"},\"48.1790217,16.3264600\":{\"foursquare\":\"Eni Ruckergasse\",\"google\":\"eni ServiceStation\",\"openstreet\":\"Agip\"}}";

        // convert JSON string to Map
        Type type = new TypeToken<Map<String, Map<String, String>>>(){}.getType();
        return gson.fromJson(json, type);
    }
}