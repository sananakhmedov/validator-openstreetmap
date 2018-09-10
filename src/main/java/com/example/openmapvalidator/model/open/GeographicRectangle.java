package com.example.openmapvalidator.model.open;

import lombok.Data;

@Data
public class GeographicRectangle {
    private String minLatitude;
    private String minLongitude;
    private String maxLatitude;
    private String maxLongitude;
}
