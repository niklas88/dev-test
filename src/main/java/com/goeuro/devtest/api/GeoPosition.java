package com.goeuro.devtest.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * GeoPosition is a simple POD class storing geographic coordinates expected to be
 * in the wgs-84 geoid. It is immutable so retrieved data doesn't get changed inadverntently.
 * <p/>
 * Created by niklas on 30.10.15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoPosition {
    public final double latitude;
    public final double longitude;

    @JsonCreator
    public GeoPosition(@JsonProperty("latitude") double latitude,
                       @JsonProperty("longitude") double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
