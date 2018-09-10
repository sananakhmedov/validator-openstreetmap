package com.example.openmapvalidator.service.statistic;

import com.example.openmapvalidator.helper.ConfigurationService;
import com.example.openmapvalidator.model.google.GoogleResult;
import com.example.openmapvalidator.service.request.GoogleRequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
/**
 * @author Sanan.Ahmadzada
 */
@Service
public class GoogleNearbyRequestHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleRequestHandler.class);

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final ConfigurationService configurationService;

    @Autowired
    public GoogleNearbyRequestHandler(RestTemplate restTemplate, ObjectMapper objectMapper, ConfigurationService configurationService) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.configurationService = configurationService;
    }

    /**
     * this method returns the number of all places within a radius based on given location in google maps
     * places.
     *
     * @return number of all google places inside of circle
     */
    public int handleNearbyWithRadius(String lat, String lon, String radius) throws InterruptedException, IOException {

        String nearbyRequestUrlOrj  = configurationService.getGOOGLE_SEARCH_NEARBY_RADIUS();

        String nearbyNextPageRequestOrj = configurationService.getGOOGLE_SEARCH_NEARBY_RADIUS_PAGETOKEN();

        String nearbyRequestUrl = nearbyRequestUrlOrj.replace("@LAT", lat)
                .replace("@LON", lon)
                .replace("@RADIUS", radius);

        String googleResultStr = restTemplate.getForObject(
                nearbyRequestUrl, String.class);

        GoogleResult result = objectMapper.readValue(googleResultStr, GoogleResult.class);
        int count = result.getResults().size();


        // TODO https://developers.google.com/places/web-service/search
        /**
         *
         * next_page_token contains a token that can be used to return
         * up to 20 additional results. A next_page_token will not be returned
         * if there are no additional results to display. The maximum number of
         * results that can be returned is 60. There is a short delay between
         * when a next_page_token is issued, and when it will become valid.
         *
         */
        while (result.getNext_page_token() != null) {

            String urlWithNextPage = nearbyNextPageRequestOrj.replace("@PAGE_TOKEN", result.getNext_page_token());

            Thread.sleep(2000);
            String googleResultStrWithNext = restTemplate.getForObject(
                    urlWithNextPage, String.class);

            result = objectMapper.readValue(googleResultStrWithNext, GoogleResult.class);
            count += result.getResults().size();

        }

        LOGGER.info("google nearby statistic returns count of : {} elements." , count);

        return count;
    }


}
