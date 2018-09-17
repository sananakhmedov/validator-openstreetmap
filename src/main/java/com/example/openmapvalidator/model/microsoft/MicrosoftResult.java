package com.example.openmapvalidator.model.microsoft;

import com.example.openmapvalidator.model.RequestModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper=true)
@Data
public class MicrosoftResult extends RequestModel {
    @JsonIgnore
    private String authenticationResultCode;
    @JsonIgnore
    private String brandLogoUri;
    @JsonIgnore
    private String copyright;
    @JsonIgnore
    private String statusCode;
    @JsonIgnore
    private String statusDescription;
    @JsonIgnore
    private String traceId;
    private List<ResourceSet> resourceSets;

    @Data
    public static class ResourceSet {
        @JsonIgnore
        private String estimatedTotal;
        private List<Resource> resources;
    }

    @Data
    public static class Resource {
        @JsonIgnore
        private String __type;
        private String isPrivateResidence;
        @JsonIgnore
        private List<AddressAtLocation> addressOfLocation;
        @JsonIgnore
        private List<NaturalPOIAtLocation> naturalPOIAtLocation;
        private List<BusinessesAtLocation> businessesAtLocation;
    }

    private static class AddressAtLocation {
    }

    @Data
    public static class BusinessesAtLocation {
        @JsonIgnore
        private BusinessAddress businessAddress;
        private BusinessInfo businessInfo;
    }

    private static class BusinessAddress {
    }

    @Data
    public static class BusinessInfo {
        private String nameOfParentTransportHub;
        private String entityName;
        private String url;
        private String phone;
        private String type;
        private String transitStopCode;
        @JsonIgnore
        private List<OtherTypes> otherTypes;
        @JsonIgnore
        private String wheelchairAccessible;
    }

    private static class OtherTypes {
    }

    private static class NaturalPOIAtLocation {
    }
}
