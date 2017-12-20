package com.example.spiros.myapplication;

/**
 * Created by Spiros2 on 20/12/2017.
 */

public class DistanceUtils {


    public static float distance (double lat, double lng, double lat1, double lng1 ) {
        double earthRadius = 3958.75;
        double latDiff = Math.toRadians(lat - lat1);
        double lngDiff = Math.toRadians(lng - lng1);
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat)) *
                Math.sin(lngDiff / 2) * Math.sin(lngDiff);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;

        double meterConversion = 1.609;

        return new Float(distance * meterConversion).floatValue();
    }
}
