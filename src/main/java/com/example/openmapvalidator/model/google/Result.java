package com.example.openmapvalidator.model.google;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class Result {
    private String place_id;
    @JsonIgnore
    private Geometry geometry;
    private String id;
    private String reference;
    private String icon;
    private String name;
    @JsonIgnore
    private OpeningHours opening_hours;
    @JsonIgnore
    private List<Photo> photos;
    @JsonIgnore
    private PlusCode plus_code;
    private String rating;
    private String scope;
    private String price_level;
    private Boolean permanently_closed;
    @JsonIgnore
    private List<Type> types;
    private String vicinity;

    private static class Geometry {
    }

    private static class OpeningHours {
    }

    private static class Photo {
    }

    private static class PlusCode {
    }

    private static class Type {
    }
}
