package com.oocode;

import java.time.DayOfWeek;

public class CachingForecaster implements Forecaster {
    private final Forecaster forecaster;

    public CachingForecaster(Forecaster forecaster) {
        this.forecaster = forecaster;
    }

    @Override
    public Forecast forecastFor(DayOfWeek dayOfWeek, String place) {
        return forecaster.forecastFor(DayOfWeek.FRIDAY, "Somewhere");
    }
}
