package com.goeuro.devtest.api;

/**
 * GeoPosition is a simple POD class storing geographic coordinates expected to be
 * in the wgs-84 geoid. It could be argued that POD is not OOP enough but this
 * is simple, clean and we can still add methods such as for calculating distances.
 * <p/>
 * <p/>
 * Created by niklas on 30.10.15.
 */
public class GeoPosition {
    public double latitude;
    public double longitude;

    public GeoPosition(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public GeoPosition(){}
}
