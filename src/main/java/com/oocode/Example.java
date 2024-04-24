package com.oocode;

import com.oocode.teamoptimization.MetOfficeWeatherForecasterClient;

import java.io.IOException;
import java.time.DayOfWeek;

public class Example {
    public static void main(String[] args) throws IOException {
        Forecaster forecaster = new MetOfficeAdapter();
        CachingForecaster cachingForecaster = new CachingForecaster(forecaster);
        printForecast(cachingForecaster);
        printForecast(cachingForecaster);
        printForecast(cachingForecaster);
        printForecast(cachingForecaster);
    }

    private static void printForecast(Forecaster forecaster) {
        String day = "Wednesday";
        String place = "Oxford";
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(day.toUpperCase());
        Forecast forecast = forecaster.forecastFor(place, dayOfWeek);
        System.out.println(forecast.toString());
    }

}
