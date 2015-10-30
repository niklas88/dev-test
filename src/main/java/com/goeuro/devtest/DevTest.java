package com.goeuro.devtest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DevTest {
    /**
     * Parses the arguments and performs sanity checks. On failure Usage is
     * printed and the program exits with a failure code
     * @param args
     * @return
     */
    public static String parseArguments(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java -jar devtest.jar <CITY_NAME>");
            System.exit(1);
        }
        return args[0];
    }


    public static void main(String[] args) {
        String cityName = parseArguments(args);
        GoEuroClient api = new GoEuroClient("http://api.goeuro.com", "en");
        try {
            List<Map<String, Object>> resultList = api.getLocationSuggestions(cityName);
            for (Map<String, Object> result : resultList){
                String name = (String) result.get("name");
                int id = (Integer) result.get("_id");
                String type = (String) result.get("type");
                Map<String, Object> geoPosition = (Map<String, Object>) result.get("geo_position");
                double latitude = (Double) geoPosition.get("latitude");
                double longitude = (Double) geoPosition.get("longitude");
                System.out.println(id+","+name+","+type+","+latitude+","+longitude);
            }
        } catch (IOException e) {
            System.err.println("ERROR: "+e.getMessage());
        }
    }
}
