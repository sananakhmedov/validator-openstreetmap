package com.example.openmapvalidator.helper;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@Data
@ComponentScan(basePackages = { "com.example.*" })
@PropertySource({"classpath:config.properties", "classpath:application.properties"})
public class ConfigurationService {

    @Value("${openstreet.getlongwithosmid}")
    private String OPENSTREET_URI_GET_LONG_WITH_OSM_ID;

    @Value("${googlemap.search.nearby}")
    private String GOOGLE_SEARCH_NEARBY;

    @Value("${foursquare.searchplacewithlong}")
    private String FOURSQUARE_URI_SEARCH_WITH_LONG;

    @Value("${microsoftmap.searchWithLong}")
    private String MICROSOFTMAP_SEARCH_WITH_LONG;

    @Value("${spring.datasource.username}")
    private String PSQL_USERNAME;

    @Value("${osm.command.database.argument}")
    private String OSM_COMMAND_DATABASE_ARGUMENT = "map-db";

    @Value("${googlemap.search.nearby.radius}")
    private String GOOGLE_SEARCH_NEARBY_RADIUS;

    @Value("${googlemap.search.nearby.radius.paketoken}")
    private String GOOGLE_SEARCH_NEARBY_RADIUS_PAGETOKEN;

    @Value("${similarity.score}")
    private String SIMILARITY_SCORE;

    @Value("${postgresql.mybatis.config.path}")
    private String MYBATIS_CONFIG_PATH;

    @Value("${similarity.algorithm}")
    private String SIMILARITY_ALGORITHM;
}
