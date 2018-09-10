package com.example.openmapvalidator.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * To Get Console output
 */
public class StreamWrapper extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(StreamWrapper.class);

    private InputStream is;
    private String type;
    private String message = null;

    public String getMessage() {
        return message;
    }

    public StreamWrapper(InputStream is, String type) {
        this.is = is;
        this.type = type;
    }

    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuffer buffer = new StringBuffer();
            String line;
            while ( (line = br.readLine()) != null) {
                LOGGER.debug(line);
                buffer.append(line);
            }
            message = buffer.toString();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}