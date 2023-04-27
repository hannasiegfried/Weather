package com.oocode;

import java.time.DayOfWeek;

public class CachingForecaster implements Forecaster {
    public CachingForecaster(Forecaster forecaster) {
    }

    @Override
    public Forecast forecastFor(DayOfWeek dayOfWeek, String place) {
        return new Forecast(1, 2, "Cold");
    }
}
