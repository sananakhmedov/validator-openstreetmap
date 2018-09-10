package com.example.openmapvalidator.model.foursquare;

import com.example.openmapvalidator.model.RequestModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
@Data
public class FoursquareResult extends RequestModel {
    private Response response;
    @JsonIgnore
    private Meta meta;
}
