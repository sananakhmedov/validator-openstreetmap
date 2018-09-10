package com.example.openmapvalidator.model.foursquare;

import lombok.Data;

import java.util.List;

@Data
public class Response {
    private List<Venue> venues;
    private boolean confident;
}
