package com.example.openmapvalidator.model.foursquare;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class Venue {
    private String id;
    private String name;
    @JsonIgnore
    private VenuePage venuePage;
    @JsonIgnore
    private Location location;
    @JsonIgnore
    private List<Category> categories;
    @JsonIgnore
    private String referralId;
    @JsonIgnore
    private boolean hasPerk;
    @JsonIgnore
    private Delivery delivery;
    @JsonIgnore
    private Events events;

    private static class Location {
    }

    private static class Category {
    }

    private static class VenuePage {
    }

    private static class Delivery {
    }

    private static class Events {
    }
}
