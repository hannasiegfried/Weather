package com.oocode;

import com.oocode.teamoptimization.MetOfficeForecasterClient;

import java.io.IOException;
import java.time.DayOfWeek;

public class Forecast {
    public int minTemp;
    public int maxTemp;
    public String description;

    public Forecast(int minTemp, int maxTemp, String description) {
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.description = description;
    }

}
