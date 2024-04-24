package com.oocode;

import kotlin.Pair;

import java.io.IOException;
import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CachingForecaster implements Forecaster{
    private Forecaster delegate;
    private int maxSize = 3;
    private LinkedHashMap<String, Forecast> cache =  new LinkedHashMap<String, Forecast>() {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String,Forecast> eldest){
            return size() > maxSize;
        }
    };

    public CachingForecaster(Forecaster delegate) {
        this.delegate = delegate;
    }

    @Override
    public Forecast forecastFor(String place, DayOfWeek dayOfWeek) {
        String key = place.concat(dayOfWeek.name());
        if(cache.containsKey(key)){
            return cache.get(key);
        }else{
            Forecast forecast = delegate.forecastFor(place, dayOfWeek);
            cache.put(key, forecast);
            return forecast;
        }
    }
}
