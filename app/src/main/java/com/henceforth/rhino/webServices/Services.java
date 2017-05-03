package com.henceforth.rhino.webServices;

/**
 * Created by HOME on 5/3/2017.
 */

public class Services {
    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    String service;

    public Services(String service) {
        this.service = service;
    }
}
