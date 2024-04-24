package com.oocode;

import java.io.IOException;
import java.time.DayOfWeek;

public class CachingForecaster implements Forecaster{
    private Forecaster delegate;

    public CachingForecaster(Forecaster delegate) {
        this.delegate = delegate;
    }

    @Override
    public Forecast forecastFor(String place, DayOfWeek dayOfWeek) {
        return delegate.forecastFor(place, dayOfWeek);
    }
}
