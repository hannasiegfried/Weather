package com.oocode;

import java.io.IOException;
import java.time.DayOfWeek;

public interface Forecaster {
    Forecast forecastFor(String place, DayOfWeek dayOfWeek);
}
