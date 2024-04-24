package com.oocode;

import com.oocode.teamoptimization.MetOfficeForecasterClient;
import com.oocode.teamoptimization.MetOfficeWeatherForecasterClient;

import java.io.IOException;
import java.time.DayOfWeek;

public class MetOfficeAdapter implements Forecaster{
    MetOfficeWeatherForecasterClient metOfficeClient = new MetOfficeWeatherForecasterClient();

    @Override
    public Forecast forecastFor(String place, DayOfWeek dayOfWeek) {

        try {
            MetOfficeForecasterClient.Forecast metOfficeForecast = metOfficeClient.getForecast(place, dayOfWeek.getValue());
            Forecast forecast = new Forecast(metOfficeForecast.minTemp, metOfficeForecast.maxTemp, metOfficeForecast.description);
            return forecast;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
