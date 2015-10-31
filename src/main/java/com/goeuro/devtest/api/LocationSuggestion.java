package com.goeuro.devtest.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * POD class for location suggestions. It is immutable so retrieved data doesn't get changed inadverntently.
 * <p/>
 * Created by niklas on 31.10.15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationSuggestion {
    public final int id;
    public final String name;
    public final String type;
    public final GeoPosition geoPosition;

    @JsonCreator
    public LocationSuggestion(@JsonProperty("_id") int id,
                              @JsonProperty("name") String name,
                              @JsonProperty("type") String type,
                              @JsonProperty("geo_position") GeoPosition geoPosition) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.geoPosition = geoPosition;
    }
}