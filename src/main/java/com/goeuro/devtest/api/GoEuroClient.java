package com.goeuro.devtest.api;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * GoEuroClient provides an easily useable though very limited
 * interface to the GoEuro API for City Name suggestions
 * <p/>
 * Created by niklas on 30.10.15.
 */
public class GoEuroClient {
    private final CloseableHttpClient httpClient;
    private final String host;
    private final ObjectMapper mapper;
    private String languageShort;
    private static final String apiVersion = "v2";

    /**
     * Build a a URI for the given verb and entity. This would likely need
     * some exapnsion to cover all of the API. All errors are locally handled as fatal.
     *
     * @param verb
     * @param entity
     * @return The built URI
     */
    private URI buildApiURI(String verb, String entity) {
        URI result = null;
        try {
            result = new URI("http",host,"/api/" + apiVersion + "/" + verb + "/" + languageShort + "/" + entity, null);
        } catch (URISyntaxException e) {
            System.err.println("INTERNAL_ERROR: A broken URI was created " + e.getMessage());
            System.exit(0xBAD);
        }
        return result;
    }


    /**
     * Construct a GoEuroClient using the given host as its
     * endpoint and runs queries for the language specified
     * as a two letter code e.g. "en" or "de"
     *
     * @param host
     * @param languageShort
     */
    public GoEuroClient(String host, String languageShort) {
        this.host = host;
        this.httpClient = HttpClients.createDefault();
        this.mapper = new ObjectMapper(new JsonFactory());
    }

    /**
     * Queries the GoEuro API for a list of location suggestions given a location's
     * name.
     *
     * @param locationName
     * @return
     * @throws IOException
     */
    public List<LocationSuggestion> getLocationSuggestions(String locationName) throws IOException {
        HttpGet httpGet = new HttpGet(buildApiURI("position/suggest", locationName));
        httpGet.addHeader("Accept", "application/json");
        CloseableHttpResponse response = httpClient.execute(httpGet);
        StatusLine statusLine = response.getStatusLine();
        if(statusLine.getStatusCode() != 200){
            throw new IOException("Error returned from Server: "+statusLine.getStatusCode()+":"+statusLine.getReasonPhrase());
        }
        HttpEntity resEntity = response.getEntity();
        BufferedInputStream content = new BufferedInputStream(resEntity.getContent());
        List<LocationSuggestion> result = mapper.readValue(content, new TypeReference<List<LocationSuggestion>>() {});
        return result;
    }
}
