package com.oocode;

import java.time.DayOfWeek;

public class LoggingForecaster implements Forecaster {
    private final Forecaster delegate;
    private final String name;

    public LoggingForecaster(String name, Forecaster delegate) {
        this.delegate = delegate;
        this.name = name;
    }

    @Override
    public Forecast forecastFor(DayOfWeek dayOfWeek, String place) throws UnableToForecast {
        try {
            Forecast result = delegate.forecastFor(dayOfWeek, place);
            System.out.printf("%s Forecast for (%s, %s) was [%s]%n", name, dayOfWeek, place, result);
            return result;
        } catch (UnableToForecast e) {
            System.out.printf("%s Forecast for (%s, %s) threw [%s]%n", name, dayOfWeek, place, e);
            throw e;
        }
    }
}
