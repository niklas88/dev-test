package com.goeuro.devtest;

import com.goeuro.devtest.api.GoEuroClient;
import com.goeuro.devtest.api.LocationSuggestion;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class DevTest {
    /**
     * Parses the arguments and performs sanity checks. On failure Usage is
     * printed and the program exits with a failure code
     * @param args
     * @return
     */
    public static String parseArguments(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java -jar GoEuroTest.jar <CITY_NAME>");
            System.exit(1);
        }
        return args[0];
    }


    public static void main(String[] args) {
        String cityName = parseArguments(args);
        GoEuroClient api = new GoEuroClient("api.goeuro.com", "en");

        try {
            PrintStream out = new PrintStream(new FileOutputStream("result.csv"));
            List<LocationSuggestion> resultList = api.getLocationSuggestions(cityName);
            // Let's also print a CSV header even though the spec isn't too clear on this
            out.println("ID,Name,Type,Latitude,Longitude");
            for (LocationSuggestion s : resultList){
                out.println(s.id+","+s.name+","+s.type+","+s.geoPosition.latitude+","+s.geoPosition.longitude);
            }
            System.out.println("Result written to result.csv");
        } catch (IOException e) {
            System.err.println("ERROR: "+e.getMessage());
        }
    }
}
