package com.oocode;

import java.time.DayOfWeek;

public class CachingForecaster implements Forecaster {
    private final Forecaster forecaster;
    private Forecast cache;

    public CachingForecaster(Forecaster forecaster) {
        this.forecaster = forecaster;
    }

    @Override
    public Forecast forecastFor(DayOfWeek dayOfWeek, String place) {
        if (cache == null) {
            cache = forecaster.forecastFor(dayOfWeek, place);
        }
        return cache;
    }
}
