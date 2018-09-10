package com.example.openmapvalidator.model;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class PlaceDBModel {
    private String osm_id;
    private String name;
    private String shop;
    private String amenity;
}
