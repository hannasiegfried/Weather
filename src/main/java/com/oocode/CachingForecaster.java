package com.oocode;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

public class CachingForecaster implements Forecaster {
    private final Forecaster forecaster;
    private final Map<String, Forecast> cache = new HashMap<>();

    public CachingForecaster(Forecaster forecaster) {
        this.forecaster = forecaster;
    }

    @Override
    public Forecast forecastFor(DayOfWeek dayOfWeek, String place) throws UnableToForecast {
        String key = dayOfWeek.name() + " " + place;
        if (!cache.containsKey(key)) {
            cache.put(key, forecaster.forecastFor(dayOfWeek, place));
        }
        return cache.get(key);
    }
}
