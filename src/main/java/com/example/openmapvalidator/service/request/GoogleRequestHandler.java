package com.example.openmapvalidator.service.request;

import com.example.openmapvalidator.helper.ConfigurationService;
import com.example.openmapvalidator.helper.Const;
import com.example.openmapvalidator.model.RequestModel;
import com.example.openmapvalidator.model.google.GoogleResult;
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
public class GoogleRequestHandler implements RequestHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleRequestHandler.class);

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final ConfigurationService configurationService;

    @Autowired
    public GoogleRequestHandler(RestTemplate restTemplate, ObjectMapper objectMapper, ConfigurationService configurationService) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.configurationService = configurationService;
    }

    public RequestModel handle(String lat, String lon) throws IOException {

        String googleUriSearch = configurationService.getGOOGLE_SEARCH_NEARBY()
                .replace(Const.LATITUDE_REPLACEMENT_SHORTCUT, lat)
                .replace(Const.LONGITUDE_REPLACEMENT_SHORTCUT, lon);

        String googleResultStr = restTemplate.getForObject(
                googleUriSearch, String.class);

        return objectMapper.readValue(googleResultStr, GoogleResult.class);
    }
}
