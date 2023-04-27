package com.oocode;

import com.teamoptimization.LocatorClient;
import com.teamoptimization.MetOfficeForecasterClient;

import java.io.IOException;
import java.time.DayOfWeek;

public class MetOfficeForecaster implements Forecaster {
    @Override
    public Forecast forecastFor(DayOfWeek dayOfWeek, String place) throws UnableToForecast {
        try {
            int dayNumber = dayOfWeek.getValue();
            LocatorClient.Location location = new LocatorClient().locationOf(place);
            MetOfficeForecasterClient forecasterClient = new MetOfficeForecasterClient();
            MetOfficeForecasterClient.Forecast forecast = forecasterClient.forecast(dayNumber, location.latitude, location.longitude);
            return new Forecast(forecast.minTemp, forecast.maxTemp, forecast.description);
        } catch (IOException e) {
            throw new UnableToForecast("Unable to get forecast from Met Office", e);
        }
    }
}
