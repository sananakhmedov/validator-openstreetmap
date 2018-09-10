package com.example.openmapvalidator.service.request;

import com.example.openmapvalidator.helper.ConfigurationService;
import com.example.openmapvalidator.helper.Const;
import com.example.openmapvalidator.model.RequestModel;
import com.example.openmapvalidator.model.foursquare.FoursquareResult;
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
public class FoursquareRequestHandler implements RequestHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(FoursquareRequestHandler.class);

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final ConfigurationService configurationService;

    @Autowired
    public FoursquareRequestHandler(RestTemplate restTemplate, ObjectMapper objectMapper, ConfigurationService configurationService) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.configurationService = configurationService;
    }

    /**
     * get detail of a place which is located in lat, lon
     *
     * @param lat latitude
     * @param lon longitude
     * @return
     */
    public RequestModel handle(String lat, String lon) throws IOException {

        String foursquareUriSearch = configurationService.getFOURSQUARE_URI_SEARCH_WITH_LONG()
                .replace(Const.LATITUDE_REPLACEMENT_SHORTCUT, lat)
                .replace(Const.LONGITUDE_REPLACEMENT_SHORTCUT, lon);

        String foursquareResultStr = restTemplate.getForObject(
                foursquareUriSearch, String.class);

        return objectMapper.readValue(foursquareResultStr, FoursquareResult.class);
    }

}
