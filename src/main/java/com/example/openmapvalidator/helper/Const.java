package com.example.openmapvalidator.helper;

import java.io.File;

public class Const {

    public static final String SPACE = " ";
    public static final String OSM_STYLE = "default.style";
    public static final String OS_NAME = "os.name";
    public static final String OS_WINDOWS_NAME = "windows";
    public static final String MAP_FOLDER_ROOT = "map";

    public static final String OSM_COMMAND_WINDOWS_ROOT = File.separator + "bashscript" + File.separator + "windows" + File
            .separator + "osm2pgsql-bin";
    public static final String OSM_COMMAND_UNIX_ROOT = File.separator + "bashscript" + File.separator + "unix" +
            File.separator + "osm2pgsql-ux";

    public static final String SHELL_BAT_NAME = "osm.bat";
    public static final String SHELL_EXIT_COMMAND = "& exit";
    public static final String OSM_BAT_FILE_PATH = OSM_COMMAND_WINDOWS_ROOT + File.separator;
    public static final String OSM_BAT_FILE_PATH_WITH_NAME = OSM_COMMAND_WINDOWS_ROOT + File.separator + SHELL_BAT_NAME;

    public static final String OSM_PSQL_PLACE_SELECT_QUERY_IDENTIFIER = "selectPlaces";
    public static final String OSM_PSQL_PLACE_COUNT_QUERY_IDENTIFIER = "countPlaces";


    public static final String OSM_COMMAND = "osm2pgsql";
    public static final String SHELL_BAT_COMMAND = "cmd /B /C start /wait";
    public static final String OSM_COMMAND_CREATE_OPTION = "--create";
    public static final String OSM_COMMAND_DATABASE_OPTION = "--database";
    public static final String OSM_COMMAND_USERNAME_OPTION = "--username";
    public static final String OSM_DEFAULT_STYLE_OPTION = "-S";

    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String RADIUS = "radius";

    public static final String NOT_PRESENT = "NOT PRESENT";

    public static final String LATITUDE_REPLACEMENT_SHORTCUT = "@LAT";
    public static final String LONGITUDE_REPLACEMENT_SHORTCUT = "@LON";
    public static final String OSMID_REPLACEMENT_SHORTCUT = "@OSM_ID";

    public static final String NUMBER_OF_GOOGLE_PLACES = "numOfGooglePlaces";
    public static final String NUMBER_OF_OPENMAP_PLACES = "numOfOpenstreetMapPlaces";

    public static final String OPENSTREET = "openstreet";
    public static final String GOOGLE = "google";
    public static final String FOURSQUARE = "foursquare";
    public static final String MICROSOFT = "microsoft";

    public static final String API_ROOT_FOLDER = "/maps";

    public static final String LEVENSHTEIN = "Levenshtein";
    public static final String JACCARD = "Jaccard";

}
