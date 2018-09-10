package com.example.openmapvalidator.service.request;

import com.example.openmapvalidator.model.RequestModel;

import java.io.IOException;
/**
 * @author Sanan.Ahmadzada
 */
public interface RequestHandler {

    /**
     * get detail of a place which is near to given latitude and longitude
     * its just applied to USA but in any short time other countries will added
     *
     * @param lat latitude and
     * @param lon longitude
     */
    RequestModel handle(String lat, String lon) throws IOException;
}
