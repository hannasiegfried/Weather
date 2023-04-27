package com.oocode;

import java.time.DayOfWeek;

public interface Forecaster {
    Forecast forecastFor(DayOfWeek dayOfWeek, String place) throws UnableToForecast;
}
