package com.example.openmapvalidator.service.request;

import com.example.openmapvalidator.helper.ConfigurationService;
import com.example.openmapvalidator.helper.Const;
import com.example.openmapvalidator.model.RequestModel;
import com.example.openmapvalidator.model.microsoft.MicrosoftResult;
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
public class MicrosoftRequestHandler implements RequestHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MicrosoftRequestHandler.class);

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final ConfigurationService configurationService;

    @Autowired
    public MicrosoftRequestHandler(RestTemplate restTemplate, ObjectMapper objectMapper, ConfigurationService configurationService) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.configurationService = configurationService;
    }

    public RequestModel handle(String lat, String lon) throws IOException {

        String microsoftUriSearch = configurationService.getMICROSOFTMAP_SEARCH_WITH_LONG()
                .replace(Const.LATITUDE_REPLACEMENT_SHORTCUT, lat)
                .replace(Const.LONGITUDE_REPLACEMENT_SHORTCUT, lon);

        String microsoftResultStr = restTemplate.getForObject(
                microsoftUriSearch, String.class);

        return objectMapper.readValue(microsoftResultStr, MicrosoftResult.class);
    }
}
