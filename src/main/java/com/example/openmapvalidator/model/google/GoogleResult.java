package com.example.openmapvalidator.model.google;

import com.example.openmapvalidator.model.RequestModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper=true)
@Data
public class GoogleResult extends RequestModel {
    private String error_message;
    private List<Result> results;
    private List<String> html_attributions;
    private String status;
    private String next_page_token;
}
