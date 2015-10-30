package com.goeuro.devtest;

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
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * GoEuroClient provides an easily useable though very limited
 * interface to the GoEuro API for City Name suggestions
 * <p/>
 * Created by niklas on 30.10.15.
 */
public class GoEuroClient {
    private final CloseableHttpClient httpClient;
    private final String baseUri;
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
            String saveEntity = URLEncoder.encode(entity, "UTF-8"); // UTF-8 what else ;-)
            result = new URI(baseUri + "/api/" + apiVersion + "/" + verb + "/" + languageShort + "/" + saveEntity);
        } catch (UnsupportedEncodingException e) {
            System.err.println("Your system does not support tUTF-8 encoding, something is seriously wrong with it.");
            // There is really no recovering in this situation
            System.exit(0xBADBEEF);
        } catch (URISyntaxException e) {
            System.err.println("INTERNAL_ERROR: A broken URI was created " + e.getMessage());
            System.exit(0xBAD);
        }
        return result;
    }


    public GoEuroClient(String baseUri, String languageShort) {
        this.baseUri = baseUri;
        this.httpClient = HttpClients.createDefault();
        this.mapper = new ObjectMapper(new JsonFactory());
    }

    public List<Map<String, Object>> getLocationSuggestions(String location) throws IOException {
        HttpGet httpGet = new HttpGet(buildApiURI("position/suggest", location));
        httpGet.addHeader("Accept", "application/json");
        CloseableHttpResponse response = httpClient.execute(httpGet);
        StatusLine statusLine = response.getStatusLine();
        if(statusLine.getStatusCode() != 200){
            throw new IOException("Error returned from Server: "+statusLine.getStatusCode()+":"+statusLine.getReasonPhrase());
        }
        HttpEntity resEntity = response.getEntity();
        BufferedInputStream content = new BufferedInputStream(resEntity.getContent());
        List<Map<String, Object>> result = mapper.readValue(content, new TypeReference<List<Map<String, Object>>>() {});
        return result;
    }
}
